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
      return null;
    }

@Override
    public HeaderBlock getHeaders() {
      return null;
}

@Override
    public boolean isCacheDisabled() {
      return false;
}

@Override
    public boolean isDirectExecutorAllowed() {
      return false;
}

@Override
    public int getPriority() {
      return 0;
}

@Override
    public boolean hasTrafficStatsTag() {
      return false;
}

@Override
    public int getTrafficStatsTag() {
      return 0;
}

@Override
    public boolean hasTrafficStatsUid() {
      return false;
}

@Override
    public int getTrafficStatsUid() {
      return 0;
}
}
