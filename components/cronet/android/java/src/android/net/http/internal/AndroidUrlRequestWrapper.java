package android.net.http;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;
import android.net.http.HeaderBlock;

import org.chromium.net.impl.CronetUrlRequest;

class AndroidUrlRequestWrapper extends android.net.http.UrlRequest
    implements ReusableWrapper<org.chromium.net.ExperimentalUrlRequest, AndroidUrlRequestWrapper> {

  private final AtomicReference<org.chromium.net.ExperimentalUrlRequest> delegate = new AtomicReference<>();
  private final boolean reusable;

  public AndroidUrlRequestWrapper() {
    reusable = true;
  }

  public AndroidUrlRequestWrapper(org.chromium.net.ExperimentalUrlRequest delegate) {
    this.delegate.set(delegate);
    reusable = false;
  }

  @Override
  public void start() {
    getDelegate().start();
  }

  @Override
  public void followRedirect() {
    getDelegate().followRedirect();
  }

  @Override
  public void read(ByteBuffer buffer) {
    getDelegate().read(buffer);
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
  public void getStatus(StatusListener listener) {
    getDelegate().getStatus(new UrlRequestStatusListenerWrapper(listener));
  }

  @Override
  public AtomicReference<org.chromium.net.ExperimentalUrlRequest> getDelegateHolder() {
    return delegate;
  }

  @Override
  public boolean isReusable() {
    return reusable;
  }

@Override
    public String getHttpMethod() {
      return getDelegate().getHttpMethod();
    }

@Override
    public HeaderBlock getHeaders() {
      return (new AndroidHeaderBlockWrapper()).withDelegate(getDelegate().getHeaders());
}

@Override
    public boolean isCacheDisabled() {
      return getDelegate().isCacheDisabled();
}

@Override
    public boolean isDirectExecutorAllowed() {
      return getDelegate().isDirectExecutorAllowed();
}

@Override
    public int getPriority() {
      return getDelegate().getPriority();
}

@Override
    public boolean hasTrafficStatsTag() {
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
}
