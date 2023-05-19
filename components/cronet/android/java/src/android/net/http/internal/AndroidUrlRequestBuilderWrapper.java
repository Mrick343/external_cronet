package android.net.http;

import org.chromium.net.ExperimentalCronetEngine;
import java.util.concurrent.Executor;
import android.net.Network;

public class AndroidUrlRequestBuilderWrapper extends android.net.http.UrlRequest.Builder {

  private final org.chromium.net.ExperimentalUrlRequest.Builder delegate;

  public AndroidUrlRequestBuilderWrapper(org.chromium.net.ExperimentalUrlRequest.Builder delegate) {
    this.delegate = delegate;
  }

  @Override
  public android.net.http.UrlRequest.Builder setHttpMethod(String method) {
    delegate.setHttpMethod(method);
    return this;
  }

  @Override
  public android.net.http.UrlRequest.Builder addHeader(String header, String value) {
    delegate.addHeader(header, value);
    return this;
  }

  @Override
  public android.net.http.UrlRequest.Builder setCacheDisabled(boolean disableCache) {
    delegate.disableCache();
    return this;
  }

  @Override
  public android.net.http.UrlRequest.Builder setPriority(int priority) {
    delegate.setPriority(priority);
    return this;
  }

  @Override
  public android.net.http.UrlRequest.Builder setUploadDataProvider(
      android.net.http.UploadDataProvider uploadDataProvider, Executor executor) {
    delegate.setUploadDataProvider(new UploadDataProviderWrapper(uploadDataProvider), executor);
    return this;
  }

  @Override
  public android.net.http.UrlRequest.Builder setDirectExecutorAllowed(boolean allowDirectExecutor) {
    delegate.allowDirectExecutor();
    return this;
  }

  @Override
  public android.net.http.UrlRequest.Builder bindToNetwork(Network network) {
    long networkHandle = ExperimentalCronetEngine.UNBIND_NETWORK_HANDLE;
    if (network != null) {
      networkHandle = network.getNetworkHandle();
    }
    delegate.bindToNetwork(networkHandle);
    return this;
  }

  @Override
  public android.net.http.UrlRequest.Builder setTrafficStatsUid(int uid) {
    delegate.setTrafficStatsUid(uid);
    return this;
  }

  @Override
  public android.net.http.UrlRequest.Builder setTrafficStatsTag(int tag) {
    delegate.setTrafficStatsTag(tag);
    return this;
  }

  @Override
  public android.net.http.UrlRequest build() {
    return new AndroidUrlRequestWrapper(delegate.build());
  }
}
