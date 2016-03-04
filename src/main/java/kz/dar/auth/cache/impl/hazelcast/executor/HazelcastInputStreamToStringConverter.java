package kz.dar.auth.cache.impl.hazelcast.executor;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sabyrzhan Tynybayev
 * @date 04/03/16
 */
public class HazelcastInputStreamToStringConverter
        implements HazelcastExecutorResponseValueConverter<String, InputStream> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastInputStreamToStringConverter.class);

    @Override
    public String convert(InputStream inputStream) {
        try {
            return IOUtils.toString(inputStream);
        } catch (IOException e) {
            LOGGER.warn("Convert error.", e);
            return null;
        }
    }
}