package hu.zsinko.rpctime;

import hu.zsinko.rpctime.client.TimeClient;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;

public class StartClient {

    public static void main(String[] args) throws IOException {

        TimeClient timeClient = new TimeClient("localhost", 4446);
        timeClient.connect();
        long currentTime = timeClient.getCurrentTime();
        System.out.println("Current time: " + new Date(currentTime));
        timeClient.close();
    }
}
