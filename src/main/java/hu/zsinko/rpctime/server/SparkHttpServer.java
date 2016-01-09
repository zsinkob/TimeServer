package hu.zsinko.rpctime.server;

import com.google.gson.Gson;
import hu.zsinko.rpctime.server.rest.TimeRequestsResponse;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class SparkHttpServer {

    public void start(Queue<Long> queue) {
        port(8080);
        staticFileLocation("/static");
        Gson gson = new Gson();
        get("/timerequests", (request, response) ->  new TimeRequestsResponse(queue.stream().map((time) -> new Date(time).toString()).collect(Collectors.toList())), gson::toJson);


    }
}
