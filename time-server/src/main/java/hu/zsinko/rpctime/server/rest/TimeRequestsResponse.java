package hu.zsinko.rpctime.server.rest;

import java.util.List;

public class TimeRequestsResponse {

    @SuppressWarnings("unused")
    private List<String> time;

    public TimeRequestsResponse(List<String> time) {
        this.time = time;
    }
}
