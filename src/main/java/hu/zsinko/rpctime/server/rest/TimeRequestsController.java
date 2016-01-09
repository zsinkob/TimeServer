package hu.zsinko.rpctime.server.rest;

import io.netty.buffer.ByteBuf;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class TimeRequestsController {

    private final Queue<Long> queue;
    private static final String RESPONSE_HEADER = "http_response_code";


    public TimeRequestsController(Queue<Long> queue) {
        this.queue = queue;
    }

    public ByteBuf create(Request request, Response response) {
        response.setResponseCreated();
        return request.getBody();
    }

    public Object read(Request request, Response response)
    {
        response.setResponseCode(200);
        List<String> timeRequests = queue.stream().map((time) -> new Date(time).toString()).collect(Collectors.toList());
        return new TimeRequestsResponse(timeRequests);
    }

    public String delete(Request request, Response response)
    {
        return "";
    }

    public ByteBuf update(Request request, Response response)
    {
        return request.getBody();
    }
}
