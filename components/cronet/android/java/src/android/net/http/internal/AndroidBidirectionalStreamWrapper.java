package android.net.http;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;
import android.net.http.HeaderBlock;

public class AndroidBidirectionalStreamWrapper extends android.net.http.BidirectionalStream
    implements ReusableWrapper<
        org.chromium.net.ExperimentalBidirectionalStream, AndroidBidirectionalStreamWrapper> {

  private final AtomicReference<org.chromium.net.ExperimentalBidirectionalStream> delegate =
      new AtomicReference<>();
  private final boolean reusable;

  AndroidBidirectionalStreamWrapper() {
    reusable = true;
  }

  AndroidBidirectionalStreamWrapper(org.chromium.net.ExperimentalBidirectionalStream delegate) {
    this.delegate.set(delegate);
    reusable = false;
  }

  @Override
  public void start() {
    getDelegate().start();
  }

  @Override
  public void read(ByteBuffer buffer) {
    getDelegate().read(buffer);
  }

  @Override
  public void write(ByteBuffer buffer, boolean endOfStream) {
    getDelegate().write(buffer, endOfStream);
  }

  @Override
  public void flush() {
    getDelegate().flush();
  }

  @Override
  public void cancel() {
    getDelegate().cancel();
  }

  @Override
  public boolean isDone() {
    return getDelegate().isDone();
  }

  @Override
  public AtomicReference<org.chromium.net.ExperimentalBidirectionalStream> getDelegateHolder() {
    return delegate;
  }

  @Override
  public boolean isReusable() {
    return reusable;
  }

  @Override
  public boolean isDelayRequestHeadersUntilFirstFlushEnabled() {
    return getDelegate().isDelayRequestHeadersUntilFirstFlushEnabled();
  }

  @Override
  public int getPriority() {
    return getDelegate().getPriority();
  }

  @Override
  public String getHttpMethod() {
    return getDelegate().getHttpMethod();
  }

    @Override
    public  boolean hasTrafficStatsTag() {
      return getDelegate().hasTrafficStatsTag();
    }

    @Override
    public int getTrafficStatsTag() {
      return getDelegate().getTrafficStatsTag();
    }

    @Override
    public boolean hasTrafficStatsUid() {
      return getDelegate().hasTrafficStatsUid();
    }
    @Override
    public int getTrafficStatsUid() {
      return getDelegate().getTrafficStatsUid();
    }

    @Override
    public HeaderBlock getHeaders() {
      return getDelegate().getHeaders();
    }

}
