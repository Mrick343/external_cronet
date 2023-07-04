package android.net.http;

import org.chromium.net.CronetException;

import java.nio.ByteBuffer;

@SuppressWarnings("Override")
class UrlRequestCallbackWrapper extends org.chromium.net.UrlRequest.Callback {

    private final android.net.http.UrlRequest.Callback backend;

    public UrlRequestCallbackWrapper(android.net.http.UrlRequest.Callback backend) {
        this.backend = backend;
    }

    @Override
    public void onRedirectReceived(
            org.chromium.net.UrlRequest request,
            org.chromium.net.UrlResponseInfo info,
            String newLocationUrl)
            throws Exception {
        ExceptionTranslationUtils.executeTranslatingExceptions(
                () -> {
                    UrlRequestWrapper wrappedRequest = new UrlRequestWrapper(request);
                    UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
                    backend.onRedirectReceived(wrappedRequest, wrappedInfo, newLocationUrl);
                    return null;
                },
                Exception.class);
    }

    @Override
    public void onResponseStarted(
            org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info)
            throws Exception {
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
    public void onReadCompleted(
            org.chromium.net.UrlRequest request,
            org.chromium.net.UrlResponseInfo info,
            ByteBuffer buffer)
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
    public void onSucceeded(
            org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info) {
                    UrlRequestWrapper wrappedRequest = new UrlRequestWrapper(request);
                    UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
                    backend.onSucceeded(wrappedRequest, wrappedInfo);
    }

    @Override
    public void onFailed(
            org.chromium.net.UrlRequest request,
            org.chromium.net.UrlResponseInfo info,
            CronetException e) {
                    UrlRequestWrapper wrappedRequest = new UrlRequestWrapper(request);
                    UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
                    HttpException translatedException =
                            ExceptionTranslationUtils.translateCheckedException(e);
                    backend.onFailed(wrappedRequest, wrappedInfo, translatedException);
    }

    @Override
    public void onCanceled(
            org.chromium.net.UrlRequest request, org.chromium.net.UrlResponseInfo info) {
                    UrlRequestWrapper wrappedRequest = new UrlRequestWrapper(request);
                    UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
                    backend.onCanceled(wrappedRequest, wrappedInfo);
    }
}
