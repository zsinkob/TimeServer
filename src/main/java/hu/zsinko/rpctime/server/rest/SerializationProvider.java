package hu.zsinko.rpctime.server.rest;

import org.restexpress.response.ErrorResponseWrapper;
import org.restexpress.response.ResponseWrapper;
import org.restexpress.serialization.AbstractSerializationProvider;
import org.restexpress.serialization.SerializationProcessor;

public class SerializationProvider
        extends AbstractSerializationProvider
{
    private static final SerializationProcessor JSON_SERIALIZER = new JsonSerializationProcessor();
    private static final ResponseWrapper RESPONSE_WRAPPER = new ErrorResponseWrapper();

    public SerializationProvider()
    {
        super();
        add(JSON_SERIALIZER, RESPONSE_WRAPPER, true);
    }

    public static SerializationProcessor json()
    {
        return JSON_SERIALIZER;
    }

}
