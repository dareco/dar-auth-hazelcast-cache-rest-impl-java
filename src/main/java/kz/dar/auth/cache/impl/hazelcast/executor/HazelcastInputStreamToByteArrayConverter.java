package kz.dar.auth.cache.impl.hazelcast.executor;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Sabyrzhan Tynybayev
 * @date 22/01/16
 */
public class HazelcastInputStreamToByteArrayConverter
        implements HazelcastExecutorResponseValueConverter<byte[], InputStream>{
    @Override
    public byte[] convert(InputStream requestResponse) {
        try {
            return IOUtils.toByteArray(requestResponse);
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
