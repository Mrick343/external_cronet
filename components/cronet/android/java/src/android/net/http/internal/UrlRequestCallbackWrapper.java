package android.net.http;

import android.net.http.HttpException;
import org.chromium.net.CronetException;
import org.chromium.net.ExperimentalUrlRequest;
import java.nio.ByteBuffer;

@SuppressWarnings("Override")
class AndroidUrlRequestCallbackWrapper extends org.chromium.net.UrlRequest.Callback {

  private final AndroidUrlRequestWrapper urlRequestWrapper = new AndroidUrlRequestWrapper();
  private final android.net.http.UrlRequest.Callback delegate;

  public AndroidUrlRequestCallbackWrapper(android.net.http.UrlRequest.Callback delegate) {
    this.delegate = delegate;
  }

  @Override
  /**
   * @see <a
   *     href="https://developer.android.com/training/basics/network-ops/reading-network-state#listening-events">Foo
   *     Bar</a>
   */
  public void onRedirectReceived(org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info, String newLocationUrl)
      throws Exception {
    CronetExceptionTranslationUtils.executeTranslatingCronetExceptions(
        () -> {
          try (AndroidUrlRequestWrapper specializedRequest =
                  urlRequestWrapper.withDelegate((ExperimentalUrlRequest)request)
                    ) {
            delegate.onRedirectReceived(
                specializedRequest,
                  new AndroidUrlResponseInfoWrapper().withDelegate(info),
                newLocationUrl);
          }
          return null;
        },
        Exception.class);
  }

  @Override
  public void onResponseStarted(org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info) throws Exception {
    CronetExceptionTranslationUtils.executeTranslatingCronetExceptions(
        () -> {
          try (AndroidUrlRequestWrapper specializedRequest =
                  urlRequestWrapper.withDelegate((ExperimentalUrlRequest)request)
                  ) {
            delegate.onResponseStarted(specializedRequest,
                  new AndroidUrlResponseInfoWrapper().withDelegate(info)
                );
          }

          return null;
        },
        Exception.class);
  }

  @Override
  public void onReadCompleted(org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info, ByteBuffer byteBuffer)
      throws Exception {
    CronetExceptionTranslationUtils.executeTranslatingCronetExceptions(
        () -> {
          try (AndroidUrlRequestWrapper specializedRequest =
                  urlRequestWrapper.withDelegate((ExperimentalUrlRequest)request)
                  ) {
            delegate.onReadCompleted(specializedRequest,
                  new AndroidUrlResponseInfoWrapper().withDelegate(info),
                byteBuffer);
          }
          ;
          return null;
        },
        Exception.class);
  }

  @Override
  public void onSucceeded(org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info) {
    try (AndroidUrlRequestWrapper specializedRequest = urlRequestWrapper.withDelegate((ExperimentalUrlRequest)request)
            ) {
      delegate.onSucceeded(specializedRequest,
                  new AndroidUrlResponseInfoWrapper().withDelegate(info));
    }
  }

  @Override
  public void onFailed(org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info, CronetException error) {
    try (AndroidUrlRequestWrapper specializedRequest = urlRequestWrapper.withDelegate((ExperimentalUrlRequest)request)
            ) {
      delegate.onFailed(
          specializedRequest,
                  new AndroidUrlResponseInfoWrapper().withDelegate(info),
          CronetExceptionTranslationUtils.translateCheckedAndroidCronetException(error));
    }
  }

  @Override
  public void onCanceled(
      org.chromium.net.UrlRequest urlRequest, org.chromium.net.UrlResponseInfo info) {
    try (AndroidUrlRequestWrapper specializedRequest = urlRequestWrapper.withDelegate((ExperimentalUrlRequest)urlRequest)
            ) {
      delegate.onCanceled(specializedRequest,
          new AndroidUrlResponseInfoWrapper().withDelegate(info));
    }
  }
}
