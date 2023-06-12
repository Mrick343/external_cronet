package android.net.http;

public class QuicExceptionWrapper extends android.net.http.QuicException {

  private final NetworkExceptionWrapper backend;

  QuicExceptionWrapper(org.chromium.net.QuicException e) {
    super(e.getMessage(), CronetExceptionTranslationUtils.translateNestedException(e.getCause()));
    this.backend = new NetworkExceptionWrapper(e, true);
  }

  @Override
  public int getErrorCode() {
    return backend.getErrorCode();
  }

  @Override
  public boolean isImmediatelyRetryable() {
    return backend.isImmediatelyRetryable();
  }
}
