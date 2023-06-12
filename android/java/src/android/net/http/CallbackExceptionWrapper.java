package android.net.http;

public class CallbackExceptionWrapper extends android.net.http.CallbackException {

  protected CallbackExceptionWrapper(org.chromium.net.CallbackException e) {
    super(e.getMessage(), ExceptionTranslationUtils.translateNestedException(e.getCause()));
  }
}
