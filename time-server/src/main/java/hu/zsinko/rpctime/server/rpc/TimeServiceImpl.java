package hu.zsinko.rpctime.server.rpc;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import hu.zsinko.rpctime.proto.TimeServerMessages;
import hu.zsinko.rpctime.proto.TimeServerServices;

import java.time.LocalTime;
import java.util.Queue;

import static hu.zsinko.rpctime.proto.TimeServerMessages.TimeRequest;
import static hu.zsinko.rpctime.proto.TimeServerMessages.TimeResponse;


public class TimeServiceImpl extends TimeServerServices.TimeService {


    private Queue<Long> queue;

    public TimeServiceImpl(Queue<Long> queue) {
        this.queue = queue;
    }

    @Override
    public void getCurrentTime(RpcController controller, TimeRequest request, RpcCallback<TimeServerMessages.TimeResponse> done) {
        TimeResponse.Builder timeResponse = TimeResponse.newBuilder();

        long currentTime = System.currentTimeMillis();
        queue.add(currentTime);
        timeResponse.setCurrentTime(currentTime);
        done.run(timeResponse.build());
    }
}
