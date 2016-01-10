package hu.zsinko.rpctime.server;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeServerConfiguration {

    public static final String TIME_SERVER_RPC_HOSTNAME = "time-server.rpc.hostname";
    public static final String TIME_SERVER_RPC_PORT = "time-server.rpc.port";
    public static final String TIME_SERVER_RPC_THREADS_MIN = "time-server.rpc.threads.min";
    public static final String TIME_SERVER_RPC_THREADS_MAX = "time-server.rpc.threads.max";
    public static final String TIME_SERVER_RPC_QUEUE_LENGTH = "time-server.rpc.queue.length";
    public static final String TIME_SERVER_HTTP_PORT = "time-server.http.port";
    private CompositeConfiguration config;

    private Logger logger = LoggerFactory.getLogger(TimeServerConfiguration.class);

    public TimeServerConfiguration() {
        this("time-server.properties");
    }

    public TimeServerConfiguration(final String configFile) {
        this.config = new CompositeConfiguration();
        config.addConfiguration(new SystemConfiguration());
        try {
            config.addConfiguration(new PropertiesConfiguration(configFile));
            logger.debug("Configuration loaded from {}", configFile);
        } catch (ConfigurationException e) {
            logger.debug("Cannot load configuration from ", configFile);
        }
    }

    public String getRpcHost() {
        return config.getString(TIME_SERVER_RPC_HOSTNAME, "localhost");
    }

    public int getRpcPort() {
        return config.getInt(TIME_SERVER_RPC_PORT, 4446);
    }

    public int getRpcMinThreads() {
        return config.getInt(TIME_SERVER_RPC_THREADS_MIN, 2);
    }

    public int getRpcMaxThreads() {
        return config.getInt(TIME_SERVER_RPC_THREADS_MAX, 10);
    }

    public int getRpcQueueLength() {
        return config.getInt(TIME_SERVER_RPC_QUEUE_LENGTH, 10);
    }

    public int getHttpPort() {
        return config.getInt(TIME_SERVER_HTTP_PORT, 8080);
    }

}
