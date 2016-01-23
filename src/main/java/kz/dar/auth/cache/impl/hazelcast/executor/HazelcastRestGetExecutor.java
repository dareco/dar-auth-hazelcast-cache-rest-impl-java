package kz.dar.auth.cache.impl.hazelcast.executor;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sabyrzhan Tynybayev
 * @date 22/10/15
 */
public class HazelcastRestGetExecutor extends HazelcastAbstractExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastRestGetExecutor.class);

    public HazelcastRestGetExecutor(HazelcastExecutorResponseValueConverter converter) {
        super(converter);
    }

    @Override
    public HazelcastExecutorResponse execute(String url, Object data) {
        HttpGet httpGet = createMethod(url);

        return execute(httpGet);
    }

    public HttpGet createMethod(String url) {
        HttpGet httpGet = new HttpGet(url);

        RequestConfig requestConfig = RequestConfig.copy(hazelcastRequestConfig).setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).build();
        httpGet.setConfig(requestConfig);
        return httpGet;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}