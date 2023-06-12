package android.net.http;

import org.chromium.net.BidirectionalStream;
import org.chromium.net.ExperimentalBidirectionalStream;
import org.chromium.net.UrlResponseInfo.HeaderBlock;
import org.chromium.net.CronetException;
import org.chromium.net.UrlResponseInfo;

import java.nio.ByteBuffer;

public class BidirectionalStreamCallbackWrapper extends org.chromium.net.ExperimentalBidirectionalStream.Callback {

  private final HeaderBlockWrapper headerBlockWrapper = new HeaderBlockWrapper();
  private final android.net.http.BidirectionalStream.Callback backend;

  public BidirectionalStreamCallbackWrapper(
      android.net.http.BidirectionalStream.Callback backend) {
    this.backend = backend;
  }

  @Override
  public void onStreamReady(BidirectionalStream stream) {
    BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
    backend.onStreamReady(wrappedStream);
  }

  @Override
  public void onResponseHeadersReceived(
    BidirectionalStream stream, UrlResponseInfo info) {
    BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
    AndroidUrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
    backend.onResponseHeadersReceived(wrappedStream, wrappedInfo);
  }

  @Override
  public void onReadCompleted(
      BidirectionalStream stream,
      UrlResponseInfo info,
      ByteBuffer byteBuffer,
      boolean endOfStream) {

    BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
    AndroidUrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
    backend.onReadCompleted(wrappedStream, wrappedInfo, byteBuffer, endOfStream);
  }

  @Override
  public void onWriteCompleted(
      BidirectionalStream bidirectionalStream,
      UrlResponseInfo urlResponseInfo,
      ByteBuffer byteBuffer,
      boolean endOfStream) {
    BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
    AndroidUrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
    backend.onWriteCompleted(wrappedStream, wrappedInfo, byteBuffer, endOfStream);
  }

  @Override
  public void onResponseTrailersReceived(
      BidirectionalStream stream,
      UrlResponseInfo info,
      HeaderBlock headers) {
    BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
    AndroidUrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
    HeaderBlockWrapper wrappedHeaders = new HeaderBlockWrapper(headers);
    backend.onResponseTrailersReceived(wrappedStream, wrappedInfo, wrappedHeaders);
  }

  @Override
  public void onSucceeded(
      BidirectionalStream stream, UrlResponseInfo info) {
    BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
    AndroidUrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
    backend.onSucceeded(wrappedStream, wrappedInfo);
  }

  @Override
  public void onFailed(
      BidirectionalStream stream, UrlResponseInfo info, CronetException e) {
    BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
    AndroidUrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
    HttpException wrappedException = ExceptionTranslationUtils.translateCheckedException(e);
    backend.onFailed(wrappedStream, wrappedInfo, wrappedException);
  }

  @Override
  public void onCanceled(
      BidirectionalStream stream, UrlResponseInfo info) {
    BidirectionalStreamWrapper wrappedStream = new BidirectionalStreamWrapper(stream);
    AndroidUrlResponseInfoWrapper wrappedInfo = new UrlResponseInfoWrapper(info);
    backend.onCanceled(wrappedStream, wrappedInfo);
  }
}
