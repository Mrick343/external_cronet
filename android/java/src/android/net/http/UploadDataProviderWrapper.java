package android.net.http;

import androidx.annotation.RequiresApi;
import java.io.IOException;
import java.nio.ByteBuffer;

@SuppressWarnings("Override")
public class UploadDataProviderWrapper extends org.chromium.net.UploadDataProvider {

  private final android.net.http.UploadDataProvider delegate;
  private final AndroidUploadDataSinkWrapper wrapper = new AndroidUploadDataSinkWrapper();

  public UploadDataProviderWrapper(android.net.http.UploadDataProvider delegate) {
    this.delegate = delegate;
  }

  @Override
  public long getLength() throws IOException {
    return delegate.getLength();
  }

  @Override
  public void read(org.chromium.net.UploadDataSink uploadDataSink, ByteBuffer byteBuffer) throws IOException {
    try (AndroidUploadDataSinkWrapper specialized = wrapper.withDelegate(uploadDataSink)) {
      delegate.read(specialized, byteBuffer);
    }
  }

  @Override
  public void rewind(org.chromium.net.UploadDataSink uploadDataSink) throws IOException {
    try (AndroidUploadDataSinkWrapper specialized = wrapper.withDelegate(uploadDataSink)) {
      delegate.rewind(specialized);
    }
  }
}