package hu.zsinko.rpctime.server.rpc;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.googlecode.protobuf.pro.duplex.RpcClient;
import com.googlecode.protobuf.pro.duplex.execute.ServerRpcController;
import hu.zsinko.rpctime.proto.TimeServerMessages;
import hu.zsinko.rpctime.proto.TimeServerServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

import static hu.zsinko.rpctime.proto.TimeServerMessages.TimeRequest;
import static hu.zsinko.rpctime.proto.TimeServerMessages.TimeResponse;


public class TimeServiceImpl extends TimeServerServices.TimeService {

    private Logger logger = LoggerFactory.getLogger(TimeServiceImpl.class);


    private Queue<Long> queue;

    public TimeServiceImpl(Queue<Long> queue) {
        this.queue = queue;
    }

    @Override
    public void getCurrentTime(RpcController controller, TimeRequest request, RpcCallback<TimeServerMessages.TimeResponse> done) {
        RpcClient rpcClient = ((ServerRpcController) controller).getRpcClient();
        logger.debug("New time request {}", rpcClient.getServerInfo());

        long currentTime = System.currentTimeMillis();
        queue.add(currentTime);

        done.run(buildResponse(currentTime));
    }

    private TimeResponse buildResponse(long currentTime) {
        TimeResponse.Builder timeResponse = TimeResponse.newBuilder();
        timeResponse.setCurrentTime(currentTime);
        return timeResponse.build();
    }
}
