package kz.dar.auth.cache.impl.hazelcast.executor;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * @author Sabyrzhan Tynybayev
 * @date 27/10/15
 */
public abstract class HazelcastAbstractExecutor implements HazelcastRestExecutor {
    protected static final RequestConfig HAZELCAST_REQUEST_CONFIG = RequestConfig.custom()
            .setCookieSpec(CookieSpecs.BEST_MATCH).setExpectContinueEnabled(true).setStaleConnectionCheckEnabled(true)
            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
            .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();

    protected static final int TIMEOUT = 10000;

    private HazelcastExecutorResponseValueConverter responseValueConverter;

    public HazelcastAbstractExecutor(HazelcastExecutorResponseValueConverter converter) {
        responseValueConverter = converter;
    }

    protected HazelcastExecutorResponse execute(HttpUriRequest uriRequest) {
        try {

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(uriRequest);

            HazelcastExecutorResponse result = new HazelcastExecutorResponse();
            result.setStatus(response.getStatusLine().getStatusCode());
            if(response.getEntity() != null) {
                if(response.getEntity().getContentLength() != 0) {
                    result.setResponse(responseValueConverter.convert(response.getEntity().getContent()));
                }

                EntityUtils.consumeQuietly(response.getEntity());
            }
            return result;
        } catch (Exception e) {
            getLogger().error("Unexpected cache error", e);
            throw new RuntimeException("Unexpected error.", e);
        }
    }

    protected abstract Logger getLogger();
}
