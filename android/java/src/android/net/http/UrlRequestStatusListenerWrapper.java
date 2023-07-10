package android.net.http;

public class UrlRequestStatusListenerWrapper extends org.chromium.net.UrlRequest.StatusListener {

    private final android.net.http.UrlRequest.StatusListener backend;

    public UrlRequestStatusListenerWrapper(android.net.http.UrlRequest.StatusListener backend) {
        this.backend = backend;
    }

    @Override
    public void onStatus(int i) {
        backend.onStatus(i);
    }
}
