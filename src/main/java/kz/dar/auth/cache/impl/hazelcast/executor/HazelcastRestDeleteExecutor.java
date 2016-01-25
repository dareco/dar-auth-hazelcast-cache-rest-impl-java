package kz.dar.auth.cache.impl.hazelcast.executor;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sabyrzhan Tynybayev
 * @date 22/10/15
 */
public class HazelcastRestDeleteExecutor extends HazelcastAbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastRestDeleteExecutor.class);

    public HazelcastRestDeleteExecutor(HazelcastExecutorResponseValueConverter converter) {
        super(converter);
    }

    @Override
    public HazelcastExecutorResponse execute(String url, Object data) {
        HttpDelete deleteMethod = new HttpDelete(url);

        RequestConfig requestConfig = RequestConfig.copy(HAZELCAST_REQUEST_CONFIG)
                .setSocketTimeout(TIMEOUT)
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT).build();

        deleteMethod.setConfig(requestConfig);

        return execute(deleteMethod);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}