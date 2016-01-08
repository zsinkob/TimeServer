package hu.zsinko.rpctime;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import hu.zsinko.rpctime.proto.TimeServerMessages;
import hu.zsinko.rpctime.proto.TimeServerServices;

import java.time.LocalTime;
import java.util.Queue;

import static hu.zsinko.rpctime.proto.TimeServerMessages.TimeRequest;
import static hu.zsinko.rpctime.proto.TimeServerMessages.TimeResponse;


public class TimeServiceImpl extends TimeServerServices.TimeService {


    private Queue<String> queue;

    public TimeServiceImpl(Queue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void getCurrentTime(RpcController controller, TimeRequest request, RpcCallback<TimeServerMessages.TimeResponse> done) {
        TimeResponse.Builder timeResponse = TimeResponse.newBuilder();

        String currentTime = LocalTime.now().toString();
        queue.add(currentTime);
        timeResponse.setCurrentTime(currentTime);
        done.run(timeResponse.build());
    }
}
