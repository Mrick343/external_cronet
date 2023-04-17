package android.net.http;

public class AndroidQuicExceptionWrapper extends android.net.http.QuicException {

  private final AndroidNetworkExceptionWrapper networkExceptionWrapper;

  AndroidQuicExceptionWrapper(org.chromium.net.QuicException delegate) {
    super(delegate.getMessage(), delegate);
    this.networkExceptionWrapper = new AndroidNetworkExceptionWrapper(delegate, true);
  }

  @Override
  public int getQuicDetailedErrorCode() {
    // TODO(danstahr: hidden API
    return 0;
  }

  @Override
  public int getErrorCode() {
    return networkExceptionWrapper.getErrorCode();
  }

  @Override
  public int getInternalErrorCode() {
    return networkExceptionWrapper.getInternalErrorCode();
  }

  @Override
  public boolean isImmediatelyRetryable() {
    return networkExceptionWrapper.isImmediatelyRetryable();
  }
}