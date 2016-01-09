package hu.zsinko.rpctime;

import hu.zsinko.rpctime.server.SparkHttpServer;
import hu.zsinko.rpctime.server.TimeRestServer;
import hu.zsinko.rpctime.server.TimeRpcServer;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Queue;

public class StartServer {

    public static void main(String[] args) {
        Queue<Long> queue = new CircularFifoQueue<>(10);

        TimeRpcServer timeRpcServer = new TimeRpcServer("localhost",4446);
        timeRpcServer.start(queue);

        /*TimeRestServer timeRestServer = new TimeRestServer("localhost",8080);
        timeRestServer.start(queue);*/
        SparkHttpServer sparkHttpServer = new SparkHttpServer();
        sparkHttpServer.start(queue);


    }
}
