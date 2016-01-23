package kz.dar.auth.cache.impl.hazelcast.executor;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * @author Sabyrzhan Tynybayev
 * @date 22/01/16
 */
public class HazelcastInputStreamToObjectConverter
        implements HazelcastExecutorResponseValueConverter<Object, InputStream> {
    @Override
    public Object convert(InputStream requestResponse) {
        try {
            return new ObjectInputStream(requestResponse).readObject();
        } catch (ClassNotFoundException | IOException e) {
            return null;
        }
    }
}
