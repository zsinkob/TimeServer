package hu.zsinko.rpctime;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import hu.zsinko.rpctime.proto.TimeServerMessages;
import hu.zsinko.rpctime.proto.TimeServerServices;

import static hu.zsinko.rpctime.proto.TimeServerMessages.*;


public class TimeServiceImpl extends TimeServerServices.TimeService {
    @Override
    public void getCurrentTime(RpcController controller, TimeRequest request, RpcCallback<TimeServerMessages.TimeResponse> done) {
        TimeResponse.Builder timeResponse = TimeResponse.newBuilder();
        timeResponse.setCurrentTime("Teszt");
        done.run(timeResponse.build());
    }
}
