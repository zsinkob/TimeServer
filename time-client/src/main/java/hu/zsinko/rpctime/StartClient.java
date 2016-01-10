package hu.zsinko.rpctime;

import hu.zsinko.rpctime.client.TimeClient;

import java.io.IOException;

public class StartClient {

    public static void main(String[] args) throws IOException {

        TimeClientConfiguration config = new TimeClientConfiguration(args);

        TimeClient timeClient = new TimeClient(config.getServerAddress(), config.getServerPort());
        timeClient.connect();

        try {
            if (config.getRepeatInterval() > 0) {
                while (true) {
                    try {
                        timeClient.getCurrentTime();
                        Thread.sleep(config.getRepeatInterval() * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                timeClient.getCurrentTime();
            }
        } finally {
            timeClient.close();
        }
    }

}
