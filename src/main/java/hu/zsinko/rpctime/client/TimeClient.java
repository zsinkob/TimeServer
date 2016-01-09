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

import java.io.IOException;

import static hu.zsinko.rpctime.proto.TimeServerMessages.TimeRequest;
import static hu.zsinko.rpctime.proto.TimeServerMessages.TimeResponse;
import static hu.zsinko.rpctime.proto.TimeServerServices.TimeService;


public class TimeClient {

    private RpcController rpcController;
    private RpcClientChannel channel;
    private PeerInfo server;
    private CleanShutdownHandler shutdownHandler;



    public TimeClient(final String serverName, final int port) {
        this.server = new PeerInfo(serverName, port);
    }

    public void connect() throws IOException {
        DuplexTcpClientPipelineFactory clientFactory = new DuplexTcpClientPipelineFactory();

        RpcServerCallExecutor executor = new ThreadPoolCallExecutor(10, 10);
        clientFactory.setRpcServerCallExecutor(executor);

        clientFactory.setConnectResponseTimeoutMillis(10000);

        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workers = new NioEventLoopGroup();
        bootstrap.group(workers);
        bootstrap.handler(clientFactory);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        bootstrap.option(ChannelOption.SO_SNDBUF, 1048576);
        bootstrap.option(ChannelOption.SO_RCVBUF, 1048576);

        shutdownHandler = new CleanShutdownHandler();
        shutdownHandler.addResource(workers);
        shutdownHandler.addResource(executor);

        channel = clientFactory.peerWith(server, bootstrap);

        rpcController = channel.newRpcController();
    }


    public long getCurrentTime() {
        TimeService.BlockingInterface timeService = TimeService.newBlockingStub(channel);

        TimeRequest.Builder timeRequest = TimeRequest.newBuilder();
        try {
            TimeResponse response = timeService.getCurrentTime(rpcController, timeRequest.build());
            return response.getCurrentTime();
        } catch (ServiceException e) {
            System.err.println(String.format("Rpc failed %s ", rpcController.errorText()));
            return 0;
        }
    }

    public void close() {
        channel.close();
        shutdownHandler.shutdown();
    }

}
