package android.net.http.wrapper;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HeaderBlockWrapper extends android.net.http.HeaderBlock {

    private final org.chromium.net.UrlResponseInfo.HeaderBlock backend;

    public HeaderBlockWrapper(org.chromium.net.UrlResponseInfo.HeaderBlock backend) {
        this.backend = backend;
    }

    @Override
    public List<Entry<String, String>> getAsList() {
        return backend.getAsList();
    }

    @Override
    public Map<String, List<String>> getAsMap() {
        return backend.getAsMap();
    }
}
