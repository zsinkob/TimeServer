package hu.zsinko.rpctime.client;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.googlecode.protobuf.pro.duplex.CleanShutdownHandler;
import com.googlecode.protobuf.pro.duplex.PeerInfo;
import com.googlecode.protobuf.pro.duplex.RpcClientChannel;
import com.googlecode.protobuf.pro.duplex.client.DuplexTcpClientPipelineFactory;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerCallExecutor;
import com.googlecode.protobuf.pro.duplex.execute.ThreadPoolCallExecutor;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static hu.zsinko.rpctime.proto.TimeServerMessages.TimeRequest;
import static hu.zsinko.rpctime.proto.TimeServerMessages.TimeResponse;
import static hu.zsinko.rpctime.proto.TimeServerServices.TimeService;


public class TimeClient {

    private Logger logger = LoggerFactory.getLogger(TimeClient.class);

    private RpcController rpcController;
    private RpcClientChannel channel;
    private PeerInfo server;
    private CleanShutdownHandler shutdownHandler;

    public TimeClient(final String serverName, final int port) {
        this.server = new PeerInfo(serverName, port);
    }

    public void connect() throws IOException {

        RpcServerCallExecutor executor = new ThreadPoolCallExecutor(10, 10);
        DuplexTcpClientPipelineFactory clientFactory = createClientFactory(executor);

        NioEventLoopGroup workers = new NioEventLoopGroup();

        Bootstrap bootstrap = getBootstrap(clientFactory, workers);

        shutdownHandler = new CleanShutdownHandler();
        shutdownHandler.addResource(executor);
        shutdownHandler.addResource(workers);

        try {
            channel = clientFactory.peerWith(server, bootstrap);
            rpcController = channel.newRpcController();
            logger.debug("Connected to {}:{}", server.getHostName(), server.getPort());
        } catch (IOException e) {
            shutdownHandler.shutdown();
            logger.debug("Cannot connect to {}:{}", server.getHostName(), server.getPort());
            throw e;
        }

    }

    public long getCurrentTime() {
        logger.debug("Requesting time from server");
        TimeService.BlockingInterface timeService = TimeService.newBlockingStub(channel);

        TimeRequest.Builder timeRequest = TimeRequest.newBuilder();
        try {
            TimeResponse response = timeService.getCurrentTime(rpcController, timeRequest.build());
            logger.debug("Time request result: {}", response.getCurrentTime());
            return response.getCurrentTime();
        } catch (ServiceException e) {
            logger.debug("Rpc request failed {} ", rpcController.errorText());
            throw new RuntimeException(e);
        }
    }

    public void close() {
        channel.close();
        shutdownHandler.shutdown();
    }

    private Bootstrap getBootstrap(DuplexTcpClientPipelineFactory clientFactory, NioEventLoopGroup workers) {
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workers);
        bootstrap.handler(clientFactory);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        bootstrap.option(ChannelOption.SO_SNDBUF, 1048576);
        bootstrap.option(ChannelOption.SO_RCVBUF, 1048576);

        return bootstrap;
    }

    private DuplexTcpClientPipelineFactory createClientFactory(RpcServerCallExecutor executor) {
        DuplexTcpClientPipelineFactory clientFactory = new DuplexTcpClientPipelineFactory();
        clientFactory.setRpcServerCallExecutor(executor);

        clientFactory.setConnectResponseTimeoutMillis(10000);
        return clientFactory;
    }

}
