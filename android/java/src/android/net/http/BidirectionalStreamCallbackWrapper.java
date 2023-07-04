package android.net.http;

import org.chromium.net.CronetException;

import java.nio.ByteBuffer;

public class BidirectionalStreamCallbackWrapper
        extends org.chromium.net.BidirectionalStream.Callback {

    private final android.net.http.BidirectionalStream.Callback backend;

    public BidirectionalStreamCallbackWrapper(
            android.net.http.BidirectionalStream.Callback backend) {
        this.backend = backend;
    }

    @Override
    public void onStreamReady(org.chromium.net.BidirectionalStream stream) {
        BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
        backend.onStreamReady(wrappedStream);
    }

    @Override
    public void onResponseHeadersReceived(org.chromium.net.BidirectionalStream stream, org.chromium.net.UrlResponseInfo info) {
        BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
        UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
        backend.onResponseHeadersReceived(wrappedStream, wrappedInfo);
    }

    @Override
    public void onReadCompleted(
            org.chromium.net.BidirectionalStream stream,
            org.chromium.net.UrlResponseInfo info,
            ByteBuffer byteBuffer,
            boolean endOfStream) {
        BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
        UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
        backend.onReadCompleted(wrappedStream, wrappedInfo, byteBuffer, endOfStream);
    }

    @Override
    public void onWriteCompleted(
            org.chromium.net.BidirectionalStream stream,
            org.chromium.net.UrlResponseInfo info,
            ByteBuffer byteBuffer,
            boolean endOfStream) {
        BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
        UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
        backend.onWriteCompleted(wrappedStream, wrappedInfo, byteBuffer, endOfStream);
    }

    @Override
    public void onResponseTrailersReceived(
            org.chromium.net.BidirectionalStream stream,
            org.chromium.net.UrlResponseInfo info,
            org.chromium.net.UrlResponseInfo.HeaderBlock headers) {
        BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
        UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
        HeaderBlockWrapper wrappedHeaders = new HeaderBlockWrapper(headers);
        backend.onResponseTrailersReceived(wrappedStream, wrappedInfo, wrappedHeaders);
    }

    @Override
    public void onSucceeded(org.chromium.net.BidirectionalStream stream, org.chromium.net.UrlResponseInfo info) {
        BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
        UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
        backend.onSucceeded(wrappedStream, wrappedInfo);
    }

    @Override
    public void onFailed(org.chromium.net.BidirectionalStream stream, org.chromium.net.UrlResponseInfo info, CronetException e) {
        BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
        UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
        HttpException wrappedException = ExceptionTranslationUtils.translateCheckedException(e);
        backend.onFailed(wrappedStream, wrappedInfo, wrappedException);
    }

    @Override
    public void onCanceled(org.chromium.net.BidirectionalStream stream, org.chromium.net.UrlResponseInfo info) {
        BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
        UrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
        backend.onCanceled(wrappedStream, wrappedInfo);
    }
}
