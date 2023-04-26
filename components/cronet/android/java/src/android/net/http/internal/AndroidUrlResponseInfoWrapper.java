package android.net.http;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import android.net.http.HeaderBlock;

public class AndroidUrlResponseInfoWrapper extends android.net.http.UrlResponseInfo
    implements ReusableWrapper<org.chromium.net.UrlResponseInfo, AndroidUrlResponseInfoWrapper> {

  private final AtomicReference<org.chromium.net.UrlResponseInfo> delegate =
      new AtomicReference<>();

  @Override
  public String getUrl() {
    return getDelegate().getUrl();
  }

  @Override
  public List<String> getUrlChain() {
    return getDelegate().getUrlChain();
  }

  @Override
  public int getHttpStatusCode() {
    return getDelegate().getHttpStatusCode();
  }

  @Override
  public String getHttpStatusText() {
    return getDelegate().getHttpStatusText();
  }

  @Override
  public HeaderBlock getHeaders() {
    return null;
  }

  @Override
  public boolean wasCached() {
    return getDelegate().wasCached();
  }

  @Override
  public String getNegotiatedProtocol() {
    return getDelegate().getNegotiatedProtocol();
  }

  @Override
  public long getReceivedByteCount() {
    return getDelegate().getReceivedByteCount();
  }

  @Override
  public AtomicReference<org.chromium.net.UrlResponseInfo> getDelegateHolder() {
    return delegate;
  }
}
