package android.net.http;

import java.nio.ByteBuffer;

class UrlRequestWrapper extends android.net.http.UrlRequest {

    private final org.chromium.net.ExperimentalUrlRequest backend;
    private final boolean reusable;

    public UrlRequestWrapper(org.chromium.net.ExperimentalUrlRequest backend) {
        this.backend = backend;
    }

    @Override
    public void start() {
        backend.start();
    }

    @Override
    public void followRedirect() {
        backend.followRedirect();
    }

    @Override
    public void read(ByteBuffer buffer) {
        backend.read(buffer);
    }

    @Override
    public void cancel() {
        backend.cancel();
    }

    @Override
    public boolean isDone() {
        return backend.isDone();
    }

    @Override
    public void getStatus(StatusListener listener) {
        backend.getStatus(new UrlRequestStatusListenerWrapper(listener));
    }

    @Override
    public boolean isReusable() {
        return reusable;
    }

    @Override
    public String getHttpMethod() {
        return backend.getHttpMethod();
    }

    @Override
    public android.net.http.HeaderBlock getHeaders() {
        org.chromium.net.UrlResponseInfo.HeaderBlock headers = backend.getHeaders();
        return new HeaderBlockWrapper(headers);
    }

    @Override
    public boolean isCacheDisabled() {
        return backend.isCacheDisabled();
    }

    @Override
    public boolean isDirectExecutorAllowed() {
        return backend.isDirectExecutorAllowed();
    }

    @Override
    public int getPriority() {
        return backend.getPriority();
    }

    @Override
    public boolean hasTrafficStatsTag() {
        return backend.hasTrafficStatsTag();
    }

    @Override
    public int getTrafficStatsTag() {
        return backend.getTrafficStatsTag();
    }

    @Override
    public boolean hasTrafficStatsUid() {
        return backend.hasTrafficStatsUid();
    }

    @Override
    public int getTrafficStatsUid() {
        return backend.getTrafficStatsUid();
    }
}
