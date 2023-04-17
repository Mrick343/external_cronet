package android.net.http;

public class AndroidCallbackExceptionWrapper extends android.net.http.CallbackException {

  protected AndroidCallbackExceptionWrapper(org.chromium.net.CallbackException delegate) {
    super(delegate.getMessage(), delegate);
  }
}