package android.net.http;

public class UrlRequestStatusListenerWrapper extends org.chromium.net.UrlRequest.StatusListener {

  private final android.net.http.UrlRequest.StatusListener delegate;

  public UrlRequestStatusListenerWrapper(android.net.http.UrlRequest.StatusListener delegate) {
    this.delegate = delegate;
  }

  @Override
  public void onStatus(int i) {
    delegate.onStatus(i);
  }
}