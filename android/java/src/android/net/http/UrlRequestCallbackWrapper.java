package android.net.http;

import android.net.http.HttpException;
import org.chromium.net.CronetException;
import java.nio.ByteBuffer;

@SuppressWarnings("Override")
class UrlRequestCallbackWrapper extends org.chromium.net.UrlRequest.Callback {

  private final UrlRequestWrapper urlRequestWrapper = new UrlRequestWrapper();
  private final android.net.http.UrlRequest.Callback backend;

  public UrlRequestCallbackWrapper(android.net.http.UrlRequest.Callback backend) {
    this.backend = backend;
  }

  @Override
  /**
   * @see <a
   *     href="https://developer.android.com/training/basics/network-ops/reading-network-state#listening-events">Foo
   *     Bar</a>
   */
  public void onRedirectReceived(org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info, String newLocationUrl)
      throws Exception {
    ExceptionTranslationUtils.executeTranslatingExceptions(
        () -> {
          UrlRequestWrapper wrappedRequest = new UrlRequestWrapper(request);
          backend.onRedirectReceived(wrappedRequest, wrappedInfo, newLocationUrl);
          return null;
        },
        Exception.class);
  }

  @Override
  public void onResponseStarted(org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info) throws Exception {
    ExceptionTranslationUtils.executeTranslatingExceptions(
        () -> {
          UrlRequestWrapper wrappedRequest = new UrlRequestWrapper(request);
          UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
          backend.onResponseStarted(wrappedRequest, wrappedInfo);
          return null;
        },
        Exception.class);
  }

  @Override
  public void onReadCompleted(org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info, ByteBuffer buffer)
      throws Exception {
    ExceptionTranslationUtils.executeTranslatingExceptions(
        () -> {
          UrlRequestWrapper wrappedRequest = new UrlRequestWrapper(request);
          UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
          backend.onReadCompleted(wrappedRequest, wrappedInfo, buffer);
          return null;
        },
        Exception.class);
  }

  @Override
  public void onSucceeded(org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info) {
    ExceptionTranslationUtils.executeTranslatingExceptions(
        () -> {
          UrlRequestWrapper wrappedRequest = new UrlRequestWrapper(request);
          UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
          backend.onSucceeded(wrappedRequest, wrappedInfo, buffer);
          return null;
        },
        Exception.class);
  }

  @Override
  public void onFailed(org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info, CronetException e) {
    ExceptionTranslationUtils.executeTranslatingExceptions(
        () -> {
          UrlRequestWrapper wrappedRequest = new UrlRequestWrapper(request);
          UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
          HttpException translatedException = ExceptionTranslationUtils.translateCheckedException(e);
          backend.onFailed(wrappedRequest, wrappedInfo, translatedException);
          return null;
        },
        Exception.class);
  }

  @Override
  public void onCanceled(
      org.chromium.net.UrlRequest urlRequest, org.chromium.net.UrlResponseInfo info) {
    ExceptionTranslationUtils.executeTranslatingExceptions(
        () -> {
          UrlRequestWrapper wrappedRequest = new UrlRequestWrapper(request);
          UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
          backend.onCanceled(wrappedRequest, wrappedInfo);
          return null;
        },
        Exception.class);
  }
}
