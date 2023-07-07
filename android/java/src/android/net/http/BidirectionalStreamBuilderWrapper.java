package android.net.http;

public class BidirectionalStreamBuilderWrapper
        extends android.net.http.BidirectionalStream.Builder {

    private final org.chromium.net.ExperimentalBidirectionalStream.Builder backend;

    public BidirectionalStreamBuilderWrapper(
            org.chromium.net.ExperimentalBidirectionalStream.Builder backend) {
        this.backend = backend;
    }

    @Override
    public android.net.http.BidirectionalStream.Builder setHttpMethod(String method) {
        backend.setHttpMethod(method);
        return this;
    }

    @Override
    public android.net.http.BidirectionalStream.Builder addHeader(String header, String value) {
        backend.addHeader(header, value);
        return this;
    }

    @Override
    public android.net.http.BidirectionalStream.Builder setPriority(int priority) {
        backend.setPriority(priority);
        return this;
    }

    @Override
    public android.net.http.BidirectionalStream.Builder
            setDelayRequestHeadersUntilFirstFlushEnabled(
                    boolean delayRequestHeadersUntilFirstFlush) {
        backend.delayRequestHeadersUntilFirstFlush(delayRequestHeadersUntilFirstFlush);
        return this;
    }

    @Override
    public android.net.http.BidirectionalStream build() {
        return new BidirectionalStreamWrapper(backend.build());
    }

    @Override
    public android.net.http.BidirectionalStream.Builder setTrafficStatsTag(int tag) {
        backend.setTrafficStatsTag(tag);
        return this;
    }

    @Override
    public android.net.http.BidirectionalStream.Builder setTrafficStatsUid(int uid) {
        backend.setTrafficStatsUid(uid);
        return this;
    }
}
