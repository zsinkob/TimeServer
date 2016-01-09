package hu.zsinko.rpctime.server;

import hu.zsinko.rpctime.server.rest.SerializationProvider;
import hu.zsinko.rpctime.server.rest.TimeRequestsController;
import org.restexpress.RestExpress;
import java.util.Queue;

public class TimeRestServer {

    final String host;
    final int port;

    public TimeRestServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start(final Queue<Long> queue) {

        RestExpress.setSerializationProvider(new SerializationProvider());

        RestExpress server = new RestExpress()
                .setName(host)
                .setPort(port);

        defineRoutes(server, queue);

        server.setIoThreadCount(5);

        server.setExecutorThreadCount(5);

        server.bind();
        server.awaitShutdown();
    }

    private static void defineRoutes(final RestExpress server, final Queue<Long> queue) {

        server.uri("/timerequests", new TimeRequestsController(queue));
    }
}
