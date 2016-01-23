package kz.dar.auth.cache.impl.hazelcast.executor;

/**
 * @author Sabyrzhan Tynybayev
 * @date 22/01/16
 */
public interface HazelcastExecutorResponseValueConverter<T, R> {
    T convert(R requestResponse);
}
