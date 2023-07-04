package android.net.http;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

public class AndroidHeaderBlockWrapper extends android.net.http.HeaderBlock
    implements ReusableWrapper<org.chromium.net.UrlResponseInfo.HeaderBlock, AndroidHeaderBlockWrapper> {

  private final AtomicReference<org.chromium.net.UrlResponseInfo.HeaderBlock> delegate = new AtomicReference<>();

  @Override
  public List<Entry<String, String>> getAsList() {
    return getDelegate().getAsList();
  }

  @Override
  public Map<String, List<String>> getAsMap() {
    return getDelegate().getAsMap();
  }

  @Override
  public AtomicReference<org.chromium.net.UrlResponseInfo.HeaderBlock> getDelegateHolder() {
    return delegate;
  }
}