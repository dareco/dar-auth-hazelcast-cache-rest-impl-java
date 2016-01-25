package kz.dar.auth.cache.impl.hazelcast.executor;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author Sabyrzhan Tynybayev
 * @date 22/10/15
 */
public class HazelcastRestAddExecutor extends HazelcastAbstractExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastRestAddExecutor.class);

    public HazelcastRestAddExecutor(HazelcastExecutorResponseValueConverter converter) {
        super(converter);
    }

    @Override
    public HazelcastExecutorResponse execute(String url, Object data) {
        HttpPost httpPost = createPostMethod(url, data);

        return execute(httpPost);
    }

    public HttpPost createPostMethod(String url, Object postData) {
        try {
            byte[] rawData;

            if(postData instanceof byte[]) {
                rawData = (byte[]) postData;
            } else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(postData);
                oos.close();
                rawData = baos.toByteArray();
            }

            InputStream is = new ByteArrayInputStream(rawData);

            HttpPost httpPost = new HttpPost(url);
            BasicHttpEntity entity = new BasicHttpEntity();
            entity.setContentType("application/octet-stream");
            entity.setContentLength(rawData.length);
            entity.setContent(is);
            httpPost.setEntity(entity);

            RequestConfig requestConfig = RequestConfig.copy(HAZELCAST_REQUEST_CONFIG)
                    .setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(TIMEOUT).build();
            httpPost.setConfig(requestConfig);
            return httpPost;
        } catch (IOException e) {
            throw new RuntimeException("Error POST-ing data", e);
        }
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
