package kz.dar.auth.cache.impl.hazelcast.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * @author Sabyrzhan Tynybayev
 * @date 22/01/16
 */
public class HazelcastInputStreamToObjectConverter<R>
        implements HazelcastExecutorResponseValueConverter<R, InputStream> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastInputStreamToObjectConverter.class);

    @Override
    public R convert(InputStream requestResponse) {
        try {
            return (R) new ObjectInputStream(requestResponse).readObject();
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.warn("Convert error.", e);
            return null;
        }
    }
}