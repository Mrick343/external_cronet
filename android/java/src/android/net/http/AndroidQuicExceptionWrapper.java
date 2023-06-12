package android.net.http;

public class AndroidQuicExceptionWrapper extends android.net.http.QuicException {

  private final AndroidNetworkExceptionWrapper networkExceptionWrapper;

  AndroidQuicExceptionWrapper(org.chromium.net.QuicException delegate) {
    super(delegate.getMessage(), CronetExceptionTranslationUtils.translateNestedException(delegate.getCause()));
    this.networkExceptionWrapper = new AndroidNetworkExceptionWrapper(delegate, true);
  }

  @Override
  public int getErrorCode() {
    return networkExceptionWrapper.getErrorCode();
  }

  @Override
  public boolean isImmediatelyRetryable() {
    return networkExceptionWrapper.isImmediatelyRetryable();
  }
}
