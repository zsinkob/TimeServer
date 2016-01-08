package hu.zsinko.rpctime;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.googlecode.protobuf.pro.duplex.CleanShutdownHandler;
import com.googlecode.protobuf.pro.duplex.PeerInfo;
import com.googlecode.protobuf.pro.duplex.RpcClientChannel;
import com.googlecode.protobuf.pro.duplex.client.DuplexTcpClientPipelineFactory;
import com.googlecode.protobuf.pro.duplex.execute.RpcServerCallExecutor;
import com.googlecode.protobuf.pro.duplex.execute.ThreadPoolCallExecutor;
import hu.zsinko.rpctime.proto.TimeServerMessages;
import hu.zsinko.rpctime.proto.TimeServerServices;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;

import static hu.zsinko.rpctime.proto.TimeServerMessages.*;

/**
 * Created by Balázs on 2016. 01. 08..
 */
public class TimeClient {

    public static void main(String[] args) throws IOException {
        PeerInfo server = new PeerInfo("localhost", 4446);

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

        RpcClientChannel channel = clientFactory.peerWith(server, bootstrap);
        CleanShutdownHandler shutdownHandler = new CleanShutdownHandler();
        shutdownHandler.addResource(workers);
        shutdownHandler.addResource(executor);


        // Call service
        TimeServerServices.TimeService.Stub myService = TimeServerServices.TimeService.newStub(channel);

        RpcController controller = channel.newRpcController();

        TimeRequest.Builder timeRequest = TimeRequest.newBuilder();
        myService.getCurrentTime(controller, timeRequest.build(),
                new RpcCallback<TimeServerMessages.TimeResponse>() {
                    public void run(TimeResponse myResponse) {
                        System.out.println("Received Response: " + myResponse);
                    }
                });
        if (controller.failed()) {
            System.err.println(String.format("Rpc failed %s ", controller.errorText()));
        }

        channel.close();


    }
}
