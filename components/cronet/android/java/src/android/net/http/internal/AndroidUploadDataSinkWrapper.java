package android.net.http;

import java.util.concurrent.atomic.AtomicReference;

class AndroidUploadDataSinkWrapper extends android.net.http.UploadDataSink
    implements ReusableWrapper<org.chromium.net.UploadDataSink, AndroidUploadDataSinkWrapper> {

  private final AtomicReference<org.chromium.net.UploadDataSink> delegate = new AtomicReference<>();

  @Override
  public void onReadSucceeded(boolean finalChunk) {
    delegate.get().onReadSucceeded(finalChunk);
  }

  @Override
  public void onReadError(Exception exception) {
    delegate.get().onReadError(exception);
  }

  @Override
  public void onRewindSucceeded() {
    delegate.get().onRewindSucceeded();
  }

  @Override
  public void onRewindError(Exception exception) {
    delegate.get().onRewindError(exception);
  }

  @Override
  public AtomicReference<org.chromium.net.UploadDataSink> getDelegateHolder() {
    return delegate;
  }
}