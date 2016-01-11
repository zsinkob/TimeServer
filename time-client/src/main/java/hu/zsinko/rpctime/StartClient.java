package hu.zsinko.rpctime;

import hu.zsinko.rpctime.client.TimeClient;
import hu.zsinko.rpctime.client.TimeClientConfiguration;

import java.io.IOException;

public class StartClient {

    public static void main(String[] args) throws IOException {

        TimeClientConfiguration config = new TimeClientConfiguration(args);

        TimeClient timeClient = new TimeClient(config.getServerAddress(), config.getServerPort());
        timeClient.connect();

        try {
            if (config.getRepeatInterval() > 0) {
                startClientLoop(config, timeClient);
            } else {
                timeClient.getCurrentTime();
            }
        } finally {
            timeClient.close();
        }
    }

    private static void startClientLoop(TimeClientConfiguration config, TimeClient timeClient) {
        while (true) {
            try {
                timeClient.getCurrentTime();
                Thread.sleep(config.getRepeatInterval() * 1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
