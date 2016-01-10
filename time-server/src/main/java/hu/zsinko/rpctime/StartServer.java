package hu.zsinko.rpctime;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Queues;
import hu.zsinko.rpctime.server.TimeServerConfiguration;
import hu.zsinko.rpctime.server.rest.TimeHttpServer;
import hu.zsinko.rpctime.server.rpc.TimeRpcServer;

import java.util.Queue;

public class StartServer {

    public static void main(String[] args) {

        TimeServerConfiguration config = new TimeServerConfiguration();

        EvictingQueue<Long> evictingQueue = EvictingQueue.create(config.getRpcQueueLength());
        Queue<Long> queue = Queues.synchronizedQueue(evictingQueue);

        TimeRpcServer timeRpcServer = new TimeRpcServer(config);
        timeRpcServer.start(queue);

        TimeHttpServer timeHttpServer = new TimeHttpServer(config);
        timeHttpServer.start(queue);

    }
}
