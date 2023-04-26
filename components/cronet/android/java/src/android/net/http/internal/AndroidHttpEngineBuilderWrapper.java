package android.net.http;

import android.util.Log;
import java.util.Date;
import java.time.Instant;
import java.util.Set;
import org.chromium.net.ExperimentalCronetEngine;
import android.net.http.HttpEngine;

public class AndroidHttpEngineBuilderWrapper extends HttpEngine.Builder {

  private static final String TAG = "HttpEngineBuilderWrap";

  private final ExperimentalCronetEngine.Builder delegate;

  public AndroidHttpEngineBuilderWrapper(ExperimentalCronetEngine.Builder delegate) {
    this.delegate = delegate;
  }

  @Override
  public String getDefaultUserAgent() {
    return delegate.getDefaultUserAgent();
  }

  @Override
  public HttpEngine.Builder setUserAgent(String userAgent) {
    delegate.setUserAgent(userAgent);
    return this;
  }

  @Override
  public HttpEngine.Builder setStoragePath(String value) {
    delegate.setStoragePath(value);
    return this;
  }

  @Override
  public HttpEngine.Builder setEnableQuic(boolean value) {
    delegate.enableQuic(value);
    return this;
  }

  @Override
  public HttpEngine.Builder setEnableHttp2(boolean value) {
    delegate.enableHttp2(value);
    return this;
  }

  @Override
  public HttpEngine.Builder setEnableBrotli(boolean value) {
    delegate.enableBrotli(value);
    return this;
  }

  @Override
  public HttpEngine.Builder setEnableHttpCache(int cacheMode, long maxSize) {
    delegate.enableHttpCache(cacheMode, maxSize);
    return this;
  }

  @Override
  public HttpEngine.Builder addQuicHint(String host, int port, int alternatePort) {
    delegate.addQuicHint(host, port, alternatePort);
    return this;
  }

  @Override
  public HttpEngine.Builder addPublicKeyPins(
      String hostName, Set<byte[]> pinsSha256, boolean includeSubdomains, Instant expirationInstant) {
    delegate.addPublicKeyPins(hostName, pinsSha256, includeSubdomains, Date.from(expirationInstant));
    return this;
  }

  @Override
  public HttpEngine.Builder setEnablePublicKeyPinningBypassForLocalTrustAnchors(boolean value) {
    delegate.enablePublicKeyPinningBypassForLocalTrustAnchors(value);
    return this;
  }

  /**
   * Build a {@link CronetEngine} using this builder's configuration.
   *
   * @return constructed {@link CronetEngine}.
   */
  public HttpEngine build() {
    return new AndroidHttpEngineWrapper(delegate.build());
  }
}
