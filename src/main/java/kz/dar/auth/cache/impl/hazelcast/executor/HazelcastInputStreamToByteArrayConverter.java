package kz.dar.auth.cache.impl.hazelcast.executor;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sabyrzhan Tynybayev
 * @date 22/01/16
 */
public class HazelcastInputStreamToByteArrayConverter
        implements HazelcastExecutorResponseValueConverter<byte[], InputStream>{

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastInputStreamToByteArrayConverter.class);

    @Override
    public byte[] convert(InputStream requestResponse) {
        try {
            return IOUtils.toByteArray(requestResponse);
        } catch (IOException e) {
            LOGGER.warn("Convert error.", e);
            return new byte[0];
        }
    }
}
