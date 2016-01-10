package hu.zsinko.rpctime.client;

import org.apache.commons.configuration.CompositeConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeClientConfiguration {

    public static final String TIME_CLIENT_SERVER_HOST = "time-client.server.host";
    public static final String TIME_CLIENT_SERVER_PORT = "time-client.server.port";
    public static final String TIME_SERVER_REPEAT_INTERVAL = "time-server.repeat.interval";
    private CompositeConfiguration config;

    private Logger logger = LoggerFactory.getLogger(TimeClientConfiguration.class);

    public TimeClientConfiguration(String[] args) {
        this.config = new CompositeConfiguration();

        if (args.length >= 2) {
            config.addProperty(TIME_CLIENT_SERVER_HOST, args[0]);
            config.addProperty(TIME_CLIENT_SERVER_PORT, args[1]);
        }
        if (args.length == 1 || args.length == 3) {
            config.addProperty(TIME_SERVER_REPEAT_INTERVAL, args[args.length - 1]);
        }
    }

    public String getServerAddress() {
        return config.getString(TIME_CLIENT_SERVER_HOST, "localhost");
    }

    public int getServerPort() {
        return config.getInt(TIME_CLIENT_SERVER_PORT, 4446);
    }

    public int getRepeatInterval() {
        return config.getInt(TIME_SERVER_REPEAT_INTERVAL, 0);
    }
}
