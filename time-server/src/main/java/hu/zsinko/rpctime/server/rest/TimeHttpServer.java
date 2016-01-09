package hu.zsinko.rpctime.server.rest;

import com.google.gson.Gson;

import java.util.Date;
import java.util.Queue;
import java.util.stream.Collectors;

import static spark.Spark.*;

public class TimeHttpServer {

    public void start(Queue<Long> queue) {
        port(8080);
        staticFileLocation("/static");
        Gson gson = new Gson();
        get("/timerequests", (request, response) -> new TimeRequestsResponse(queue.stream().map((time) -> new Date(time).toString()).collect(Collectors.toList())), gson::toJson);


    }
}
