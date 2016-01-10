package hu.zsinko.rpctime.server.rest;

import com.google.gson.Gson;
import hu.zsinko.rpctime.server.TimeServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class TimeHttpServer {

    private final TimeServerConfiguration config;

    private Logger logger = LoggerFactory.getLogger(TimeHttpServer.class);

    public TimeHttpServer(TimeServerConfiguration config) {
        this.config = config;
    }

    public void start(Queue<Long> queue) {
        logger.info("Starting HTTP server on port {}", config.getHttpPort());
        port(config.getHttpPort());
        staticFileLocation("/static");
        logger.info("/static endpoint configured");
        Gson gson = new Gson();
        get("/timerequests", buildResponse(queue), gson::toJson);
        logger.info("/timerequests endpoint configured");
    }

    private Route buildResponse(Queue<Long> queue) {
        return (request, response) -> {
            List<String> timeRequests = queue.stream().map((time) -> new Date(time).toString()).collect(Collectors.toList());
            return new TimeRequestsResponse(timeRequests);
        };
    }
}
