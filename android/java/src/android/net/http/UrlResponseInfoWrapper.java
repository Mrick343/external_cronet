package android.net.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UrlResponseInfoWrapper extends android.net.http.UrlResponseInfo {

    private final org.chromium.net.UrlResponseInfo backend;

    public UrlResponseInfoWrapper(org.chromium.net.UrlResponseInfo backend) {
      this.backend = backend;
    }

    @Override
    public String getUrl() {
        return backend.getUrl();
    }

    @Override
    public List<String> getUrlChain() {
        return backend.getUrlChain();
    }

    @Override
    public int getHttpStatusCode() {
        return backend.getHttpStatusCode();
    }

    @Override
    public String getHttpStatusText() {
        return backend.getHttpStatusText();
    }

    @Override
    public android.net.http.HeaderBlock getHeaders() {
        return new HeaderBlockImpl(Collections.unmodifiableList(backend.getAllHeadersAsList()));
    }

    @Override
    public boolean wasCached() {
        return backend.wasCached();
    }

    @Override
    public String getNegotiatedProtocol() {
        return backend.getNegotiatedProtocol();
    }

    @Override
    public long getReceivedByteCount() {
        return backend.getReceivedByteCount();
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
                List<String> values = map.computeIfAbsent(entry.getKey(), key -> new ArrayList<>());
                values.add(entry.getValue());
            }
            map.replaceAll((key, values) -> Collections.unmodifiableList(values));
            mHeadersMap = Collections.unmodifiableMap(map);
            return mHeadersMap;
        }
    }
}
