package kz.dar.auth.cache.impl.hazelcast;

import com.google.common.base.Preconditions;
import kz.dar.auth.cache.CacheService;
import kz.dar.auth.cache.exception.CacheObjectNotFoundException;
import kz.dar.auth.cache.impl.hazelcast.executor.HazelcastExecutorResponse;
import kz.dar.auth.cache.impl.hazelcast.executor.HazelcastRestExecutor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sabyrzhan Tynybayev
 * @date 22/10/15
 */
public class HazelcastRestCacheService implements CacheService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastRestCacheService.class);

    @Inject
    private HazelcastRestExecutor addExecutor;

    @Inject
    private HazelcastRestExecutor deleteExecutor;

    @Inject
    private HazelcastRestExecutor getExecutor;

    private List<String> endpointUrls;

    public void setEndpointsAndMapName(List<String> endpoints, String mapName) {
        this.endpointUrls = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        for(String endPoint: endpoints) {
            endpointUrls.add(sb.append("http://").append(endPoint).append("/hazelcast/rest/maps/").append(mapName).toString());
            sb.setLength(0);
        }
    }

    @Override
    public void add(String s, Object o) {
        Preconditions.checkArgument(StringUtils.isNotBlank(s), "key is empty");
        Preconditions.checkNotNull(o, "value is null");

        execute(o, s, addExecutor);
    }

    @Override
    public Object get(String s) throws CacheObjectNotFoundException {
        Object data = execute(null, s, getExecutor);
        if(data == null) {
            throw new CacheObjectNotFoundException(s);
        }

        return data;
    }

    @Override
    public void delete(String s) {
        execute(null, s, deleteExecutor);
    }

    private Object execute(Object data, String key, HazelcastRestExecutor executor) {
        HazelcastExecutorResponse responseEntity;

        boolean success = false;
        Object result = null;

        String url;
        for(String endpoint : endpointUrls) {
            try {
                url = endpoint;

                if(key != null) {
                    url = endpoint + "/" + key;
                }

                responseEntity = executor.execute(url, data);

                if(responseEntity == null) {
                    LOGGER.warn("Response from hazelcast cache for url={} is NULL!!!", url);
                    continue;
                }

                if(responseEntity.getStatus() == 200 || responseEntity.getStatus() == 204) {
                    success = true;
                    result = responseEntity.getResponse();
                    break;
                } else {
                    throw new RuntimeException(
                            "Invalid response from cache service " + responseEntity.toString() + " for endpoint=" + endpoint);
                }
            } catch (Exception e) {
                LOGGER.error("Hazelcast request error.", e);
            }
        }

        if(!success) {
            throw new RuntimeException("Cache service error");
        }

        return result;
    }
}