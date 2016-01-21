package kz.dar.auth.cache.impl.hazelcast.executor;

/**
 * @author Sabyrzhan Tynybayev
 * @date 28/10/15
 */
public class HazelcastExecutorResponse {
    private int status;
    private Object response;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HazelcastExecutorResponse{");
        sb.append("status=").append(status);
        sb.append(", response='").append(response).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
