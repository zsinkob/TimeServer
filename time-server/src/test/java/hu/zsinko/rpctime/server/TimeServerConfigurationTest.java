package hu.zsinko.rpctime.server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeServerConfigurationTest {

    @Test
    public void testLoadRpcHost() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration();
        assertEquals("test-rpc", config.getRpcHost());
    }

    @Test
    public void testLoadRpcHostDefaultValue() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration("test");
        assertEquals("localhost", config.getRpcHost());
    }

    @Test
    public void testLoadRpcPort() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration();
        assertEquals(9991, config.getRpcPort());
    }

    @Test
    public void testLoadRpcPortDefaultValue() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration("test");
        assertEquals(4446, config.getRpcPort());
    }

    @Test
    public void testLoadThreadsMin() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration();
        assertEquals(9992, config.getRpcMinThreads());
    }

    @Test
    public void testLoadThreadsMinDefault() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration("test");
        assertEquals(2, config.getRpcMinThreads());
    }

    @Test
    public void testLoadThreadsMax() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration();
        assertEquals(9993, config.getRpcMaxThreads());
    }

    @Test
    public void testLoadThreadsMaxDefaultValue() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration("test");
        assertEquals(10, config.getRpcMaxThreads());
    }

    @Test
    public void testLoadQueueLength() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration();
        assertEquals(9994, config.getRpcQueueLength());
    }

    @Test
    public void testLoadQueueLengthDefaultValue() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration("test");
        assertEquals(10, config.getRpcQueueLength());
    }

    @Test
    public void testLoadHttpPort() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration();
        assertEquals(9995, config.getHttpPort());
    }

    @Test
    public void testLoadHttpPortDefaultValue() throws Exception {
        TimeServerConfiguration config = new TimeServerConfiguration("test");
        assertEquals(8080, config.getHttpPort());
    }
}