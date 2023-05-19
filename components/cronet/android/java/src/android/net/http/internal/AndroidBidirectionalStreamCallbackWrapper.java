package android.net.http;

import org.chromium.net.BidirectionalStream;
import org.chromium.net.ExperimentalBidirectionalStream;
import org.chromium.net.UrlResponseInfo.HeaderBlock;
import org.chromium.net.CronetException;
import org.chromium.net.UrlResponseInfo;
import java.nio.ByteBuffer;

public class AndroidBidirectionalStreamCallbackWrapper extends org.chromium.net.ExperimentalBidirectionalStream.Callback {

  private final AndroidBidirectionalStreamWrapper bidirectionalStreamWrapper =
      new AndroidBidirectionalStreamWrapper();
  private final AndroidHeaderBlockWrapper headerBlockWrapper = new AndroidHeaderBlockWrapper();
  private final android.net.http.BidirectionalStream.Callback delegate;

  public AndroidBidirectionalStreamCallbackWrapper(
      android.net.http.BidirectionalStream.Callback delegate) {
    this.delegate = delegate;
  }

  @Override
  public void onStreamReady(BidirectionalStream bidirectionalStream) {
    try (AndroidBidirectionalStreamWrapper specializedStream =
        bidirectionalStreamWrapper.withDelegate((ExperimentalBidirectionalStream)bidirectionalStream)) {
      delegate.onStreamReady(specializedStream);
    }
  }

  @Override
  public void onResponseHeadersReceived(
      BidirectionalStream bidirectionalStream, UrlResponseInfo urlResponseInfo) {
    try (
        AndroidBidirectionalStreamWrapper specializedStream =
            bidirectionalStreamWrapper.withDelegate((ExperimentalBidirectionalStream)bidirectionalStream)) {
      delegate.onResponseHeadersReceived(specializedStream,
            new AndroidUrlResponseInfoWrapper().withDelegate(urlResponseInfo)
          );
    }
  }

  @Override
  public void onReadCompleted(
      BidirectionalStream bidirectionalStream,
      UrlResponseInfo urlResponseInfo,
      ByteBuffer byteBuffer,
      boolean endOfStream) {

    try (
        AndroidBidirectionalStreamWrapper specializedStream =
            bidirectionalStreamWrapper.withDelegate((ExperimentalBidirectionalStream)bidirectionalStream)) {
      delegate.onReadCompleted(specializedStream,
          new AndroidUrlResponseInfoWrapper().withDelegate(urlResponseInfo),
          byteBuffer, endOfStream);
    }
  }

  @Override
  public void onWriteCompleted(
      BidirectionalStream bidirectionalStream,
      UrlResponseInfo urlResponseInfo,
      ByteBuffer byteBuffer,
      boolean endOfStream) {
    try (
        AndroidBidirectionalStreamWrapper specializedStream =
            bidirectionalStreamWrapper.withDelegate((ExperimentalBidirectionalStream)bidirectionalStream)) {
      delegate.onWriteCompleted(
          specializedStream,
            new AndroidUrlResponseInfoWrapper().withDelegate(urlResponseInfo),
          byteBuffer, endOfStream);
    }
  }

  @Override
  public void onResponseTrailersReceived(
      BidirectionalStream bidirectionalStream,
      UrlResponseInfo urlResponseInfo,
      HeaderBlock headerBlock) {
    try (
        AndroidBidirectionalStreamWrapper specializedStream =
            bidirectionalStreamWrapper.withDelegate((ExperimentalBidirectionalStream)bidirectionalStream);
        AndroidHeaderBlockWrapper specializedHeaderBlock =
            headerBlockWrapper.withDelegate(headerBlock)) {
      delegate.onResponseTrailersReceived(
          specializedStream,
        new AndroidUrlResponseInfoWrapper().withDelegate(urlResponseInfo),
          specializedHeaderBlock);
    }
  }

  @Override
  public void onSucceeded(
      BidirectionalStream bidirectionalStream, UrlResponseInfo urlResponseInfo) {
    try (
        AndroidBidirectionalStreamWrapper specializedStream =
            bidirectionalStreamWrapper.withDelegate((ExperimentalBidirectionalStream)bidirectionalStream)) {
      delegate.onSucceeded(specializedStream,
            new AndroidUrlResponseInfoWrapper().withDelegate(urlResponseInfo)
          );
    }
  }

  @Override
  public void onFailed(
      BidirectionalStream bidirectionalStream, UrlResponseInfo urlResponseInfo, CronetException e) {
    try (
        AndroidBidirectionalStreamWrapper specializedStream =
            bidirectionalStreamWrapper.withDelegate((ExperimentalBidirectionalStream)bidirectionalStream)) {
      delegate.onFailed(
          specializedStream,
            new AndroidUrlResponseInfoWrapper().withDelegate(urlResponseInfo),
          CronetExceptionTranslationUtils.translateCheckedAndroidCronetException(e)
          );
    }
  }

  @Override
  public void onCanceled(
      BidirectionalStream bidirectionalStream, UrlResponseInfo urlResponseInfo) {
    try (
        AndroidBidirectionalStreamWrapper specializedStream =
            bidirectionalStreamWrapper.withDelegate((ExperimentalBidirectionalStream)bidirectionalStream)) {
      delegate.onCanceled(specializedStream,
            new AndroidUrlResponseInfoWrapper().withDelegate(urlResponseInfo)
          );
    }
  }
}
