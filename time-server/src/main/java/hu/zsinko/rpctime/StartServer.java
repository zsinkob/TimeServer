package hu.zsinko.rpctime;

import hu.zsinko.rpctime.server.rest.TimeHttpServer;
import hu.zsinko.rpctime.server.rpc.TimeRpcServer;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Queue;

public class StartServer {

    public static void main(String[] args) {
        Queue<Long> queue = new CircularFifoQueue<>(10);

        TimeRpcServer timeRpcServer = new TimeRpcServer("localhost", 4446);
        timeRpcServer.start(queue);

        TimeHttpServer timeHttpServer = new TimeHttpServer();
        timeHttpServer.start(queue);


    }
}
