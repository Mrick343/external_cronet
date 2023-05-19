package android.net.http;

import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
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

  private static class HeaderBlockImpl extends android.net.http.HeaderBlock {
    private final List<Map.Entry<String, String>> mAllHeadersList;
    private Map<String, List<String>> mHeadersMap;

    public HeaderBlockImpl(List<Map.Entry<String, String>> allHeadersList) {
        mAllHeadersList = Collections.unmodifiableList(allHeadersList);
    }

    @Override
    public List<Map.Entry<String, String>> getAsList() {
        return mAllHeadersList;
    }

    @Override
    public Map<String, List<String>> getAsMap() {
        // This is potentially racy...but races will only result in wasted resource.
        if (mHeadersMap != null) {
            return mHeadersMap;
        }
        Map<String, List<String>> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Map.Entry<String, String> entry : mAllHeadersList) {
            List<String> values = map.computeIfAbsent(
                    entry.getKey(),
                    key -> new ArrayList<>());
            values.add(entry.getValue());
        }
        map.replaceAll((key, values) -> Collections.unmodifiableList(values));
        mHeadersMap = Collections.unmodifiableMap(map);
        return mHeadersMap;
    }
  }

  @Override
  public HeaderBlock getHeaders() {
    return new HeaderBlockImpl(getDelegate().getAllHeadersAsList());
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
