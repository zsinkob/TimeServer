package hu.zsinko.rpctime.server.rpc;

import com.googlecode.protobuf.pro.duplex.CleanShutdownHandler;
import com.googlecode.protobuf.pro.duplex.PeerInfo;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerCallExecutor;
import com.googlecode.protobuf.pro.duplex.execute.ThreadPoolCallExecutor;
import com.googlecode.protobuf.pro.duplex.server.DuplexTcpServerPipelineFactory;
import com.googlecode.protobuf.pro.duplex.util.RenamingThreadFactoryProxy;
import hu.zsinko.rpctime.server.TimeServerConfiguration;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.Executors;

public class TimeRpcServer {

    private final TimeServerConfiguration config;
    private CleanShutdownHandler shutdownHandler;

    private Logger logger = LoggerFactory.getLogger(TimeRpcServer.class);


    public TimeRpcServer(TimeServerConfiguration config) {
        this.config = config;
    }

    public void start(final Queue<Long> queue) {
        logger.info("Starting RPC server");
        PeerInfo serverInfo = new PeerInfo(config.getRpcHost(), config.getRpcPort());
        RpcServerCallExecutor executor = new ThreadPoolCallExecutor(config.getRpcMinThreads(), config.getRpcMaxThreads());

        DuplexTcpServerPipelineFactory serverFactory = new DuplexTcpServerPipelineFactory(serverInfo);
        serverFactory.setRpcServerCallExecutor(executor);

        EventLoopGroup boss = new NioEventLoopGroup(0, new RenamingThreadFactoryProxy("boss", Executors.defaultThreadFactory()));
        EventLoopGroup workers = new NioEventLoopGroup(0, new RenamingThreadFactoryProxy("worker", Executors.defaultThreadFactory()));

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, workers);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(serverFactory);
        bootstrap.localAddress(serverInfo.getPort());

        serverFactory.getRpcServiceRegistry().registerService(new TimeServiceImpl(queue));

        shutdownHandler = new CleanShutdownHandler();
        shutdownHandler.addResource(boss);
        shutdownHandler.addResource(workers);
        shutdownHandler.addResource(executor);

        bootstrap.bind();
        logger.info("RPC server started on port {}", config.getRpcPort());
    }

    public void close() {
        shutdownHandler.shutdown();
    }

}
