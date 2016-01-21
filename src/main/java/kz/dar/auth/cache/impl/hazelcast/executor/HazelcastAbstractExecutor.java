package kz.dar.auth.cache.impl.hazelcast.executor;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.ObjectInputStream;

/**
 * @author Sabyrzhan Tynybayev
 * @date 27/10/15
 */
public abstract class HazelcastAbstractExecutor implements HazelcastRestExecutor {
    @Inject
    protected HttpClient hazelcastHttpClient;

    @Inject
    protected RequestConfig hazelcastRequestConfig;

    protected static final int TIMEOUT = 1000;

    protected HazelcastExecutorResponse execute(HttpUriRequest uriRequest) {
        try {
            HttpResponse response = hazelcastHttpClient.execute(uriRequest);

            HazelcastExecutorResponse result = new HazelcastExecutorResponse();
            result.setStatus(response.getStatusLine().getStatusCode());
            if(response.getEntity() == null) {
                result.setResponse("");
            } else {
                if(response.getEntity().getContentLength() != 0) {
                    result.setResponse(new ObjectInputStream(response.getEntity().getContent()).readObject());
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            }

            return result;
        } catch (Exception e) {
            getLogger().error("Unexpected cache error", e);
            throw new RuntimeException("Unexpected error.", e);
        }
    }

    protected abstract Logger getLogger();
}
