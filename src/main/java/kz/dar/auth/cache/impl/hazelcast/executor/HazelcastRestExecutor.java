package kz.dar.auth.cache.impl.hazelcast.executor;

/**
 * @author Sabyrzhan Tynybayev
 * @date 22/10/15
 */
public interface HazelcastRestExecutor {
    HazelcastExecutorResponse execute(String url, Object data);
}
