package android.net.http;

public class AndroidBidirectionalStreamBuilderWrapper
    extends android.net.http.BidirectionalStream.Builder {

  private final org.chromium.net.ExperimentalBidirectionalStream.Builder delegate;

  public AndroidBidirectionalStreamBuilderWrapper(org.chromium.net.ExperimentalBidirectionalStream.Builder delegate) {
    this.delegate = delegate;
  }
  @Override
  public android.net.http.BidirectionalStream.Builder setHttpMethod(String method) {
    delegate.setHttpMethod(method);
    return this;
  }
  @Override
  public android.net.http.BidirectionalStream.Builder addHeader(
      String header, String value) {
    delegate.addHeader(header, value);
    return this;
  }
  @Override
  public android.net.http.BidirectionalStream.Builder setPriority(int priority) {
    delegate.setPriority(priority);
    return this;
  }
  @Override
  public android.net.http.BidirectionalStream.Builder
      setDelayRequestHeadersUntilFirstFlushEnabled(boolean delayRequestHeadersUntilFirstFlush) {
    delegate.delayRequestHeadersUntilFirstFlush(delayRequestHeadersUntilFirstFlush);
    return this;
  }

  @Override
  public android.net.http.BidirectionalStream build() {
    return new AndroidBidirectionalStreamWrapper(delegate.build());
  }

  @Override
  public android.net.http.BidirectionalStream.Builder setTrafficStatsTag(int tag) {
    delegate.setTrafficStatsTag(tag);
    return this;

  }

  @Override
  public android.net.http.BidirectionalStream.Builder setTrafficStatsUid(int uid){
    delegate.setTrafficStatsUid(uid);
    return this;

  }

}
