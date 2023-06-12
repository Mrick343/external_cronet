package android.net.http;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;

public class BidirectionalStreamWrapper extends android.net.http.BidirectionalStream {

  private final org.chromium.net.ExperimentalBidirectionalStream backend;

  AndroidBidirectionalStreamWrapper(org.chromium.net.ExperimentalBidirectionalStream backend) {
    this.backend = backend;
  }

  @Override
  public void start() {
    backend.start();
  }

  @Override
  public void read(ByteBuffer buffer) {
    backend.read(buffer);
  }

  @Override
  public void write(ByteBuffer buffer, boolean endOfStream) {
    backend.write(buffer, endOfStream);
  }

  @Override
  public void flush() {
    backend.flush();
  }

  @Override
  public void cancel() {
    backend.cancel();
  }

  @Override
  public boolean isDone() {
    return backend.isDone();
  }

  @Override
  public boolean isDelayRequestHeadersUntilFirstFlushEnabled() {
    return backend.isDelayRequestHeadersUntilFirstFlushEnabled();
  }

  @Override
  public int getPriority() {
    return backend.getPriority();
  }

  @Override
  public String getHttpMethod() {
    return backend.getHttpMethod();
  }

  @Override
  public  boolean hasTrafficStatsTag() {
    return backend.hasTrafficStatsTag();
  }

  @Override
  public int getTrafficStatsTag() {
    return backend.getTrafficStatsTag();
  }

  @Override
  public boolean hasTrafficStatsUid() {
    return backend.hasTrafficStatsUid();
  }

  @Override
  public int getTrafficStatsUid() {
    return backend.getTrafficStatsUid();
  }

  @Override
  public android.net.http.HeaderBlock getHeaders() {
    return HeaderBlockWrapper(backend.getHeaders());
  }
}
