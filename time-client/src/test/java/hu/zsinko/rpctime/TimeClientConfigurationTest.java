package hu.zsinko.rpctime;

import hu.zsinko.rpctime.client.TimeClientConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeClientConfigurationTest {

    private final String[] NO_ARGS = new String[]{};

    private final String[] REPEAT_ONLY = new String[]{"9991"};

    private final String[] HOSTNAME = new String[]{"test", "9992"};

    private final String[] HOSTNAME_REPEAT = new String[]{"test", "9993", "9994"};

    @Test
    public void testParseHost() throws Exception {
        TimeClientConfiguration timeClientConfiguration = new TimeClientConfiguration(HOSTNAME);
        assertEquals("test", timeClientConfiguration.getServerAddress());
    }

    @Test
    public void testParseHostDefault() throws Exception {
        TimeClientConfiguration timeClientConfiguration = new TimeClientConfiguration(NO_ARGS);
        assertEquals("localhost", timeClientConfiguration.getServerAddress());
    }

    @Test
    public void testParsePort() throws Exception {
        TimeClientConfiguration timeClientConfiguration = new TimeClientConfiguration(HOSTNAME);
        assertEquals(9992, timeClientConfiguration.getServerPort());
    }

    @Test
    public void testParsePortDefault() throws Exception {
        TimeClientConfiguration timeClientConfiguration = new TimeClientConfiguration(NO_ARGS);
        assertEquals(4446, timeClientConfiguration.getServerPort());
    }

    @Test
    public void testRepeatInterval() throws Exception {
        TimeClientConfiguration timeClientConfiguration = new TimeClientConfiguration(REPEAT_ONLY);
        assertEquals(9991, timeClientConfiguration.getRepeatInterval());
    }

    @Test
    public void testRepeatIntervalDefault() throws Exception {
        TimeClientConfiguration timeClientConfiguration = new TimeClientConfiguration(NO_ARGS);
        assertEquals(0, timeClientConfiguration.getRepeatInterval());
    }

    @Test
    public void testRepeatIntervalWithHost() throws Exception {
        TimeClientConfiguration timeClientConfiguration = new TimeClientConfiguration(HOSTNAME_REPEAT);
        assertEquals(9994, timeClientConfiguration.getRepeatInterval());
    }
}