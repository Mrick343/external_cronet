package android.net.http;

import java.io.IOException;
import java.nio.ByteBuffer;

@SuppressWarnings("Override")
public class UploadDataProviderWrapper extends org.chromium.net.UploadDataProvider {

  private final android.net.http.UploadDataProvider backend;

  public UploadDataProviderWrapper(android.net.http.UploadDataProvider backend) {
    this.backend = backend;
  }

  @Override
  public long getLength() throws IOException {
    return backend.getLength();
  }

  @Override
  public void read(org.chromium.net.UploadDataSink sink, ByteBuffer buffer) throws IOException {
    UploadDataSinkWrapper wrappedSink = new UploadDataSinkWrapper(sink);
    backend.read(wrappedSink, buffer);
  }

  @Override
  public void rewind(org.chromium.net.UploadDataSink sink) throws IOException {
    UploadDataSinkWrapper wrappedSink = new UploadDataSinkWrapper(sink);
    backend.rewind(wrappedSink);
  }
}
