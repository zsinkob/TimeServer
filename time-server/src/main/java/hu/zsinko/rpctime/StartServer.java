package hu.zsinko.rpctime;

import hu.zsinko.rpctime.server.TimeServerConfiguration;
import hu.zsinko.rpctime.server.rest.TimeHttpServer;
import hu.zsinko.rpctime.server.rpc.TimeRpcServer;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Queue;

public class StartServer {

    public static void main(String[] args) {

        TimeServerConfiguration config = new TimeServerConfiguration();
        Queue<Long> queue = new CircularFifoQueue<>(config.getRpcQueueLength());

        TimeRpcServer timeRpcServer = new TimeRpcServer(config);
        timeRpcServer.start(queue);

        TimeHttpServer timeHttpServer = new TimeHttpServer(config);
        timeHttpServer.start(queue);

    }
}
