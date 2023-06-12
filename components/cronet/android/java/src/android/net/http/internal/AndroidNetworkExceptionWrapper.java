package android.net.http;

public class AndroidNetworkExceptionWrapper extends android.net.http.NetworkException {

  private final org.chromium.net.NetworkException mDelegate;

  AndroidNetworkExceptionWrapper(org.chromium.net.NetworkException delegate) {
    this(delegate, false);
  }

  AndroidNetworkExceptionWrapper(
      org.chromium.net.NetworkException delegate, boolean expectQuicException) {
    super(delegate.getMessage(), CronetExceptionTranslationUtils.translateNestedException(delegate.getCause()));
    this.mDelegate = delegate;

    if (!expectQuicException && delegate instanceof org.chromium.net.QuicException) {
      throw new IllegalArgumentException(
          "Translating QuicException as NetworkException results in loss of information. Make sure"
              + " you handle QuicException first. See the stacktrace for where the translation is"
              + " being performed, and the cause for the exception being translated.");
    }
  }

  @Override
  public int getErrorCode() {
    return mDelegate.getErrorCode();
  }

  @Override
  public boolean isImmediatelyRetryable() {
    return mDelegate.immediatelyRetryable();
  }
}
