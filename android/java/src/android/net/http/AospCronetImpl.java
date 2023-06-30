package android.net.http;

import org.chromium.net.impl.CronetImpl;
import org.chromium.net.impl.CronetSource;

/**
 * AOSP-specific implementation of CronetImpl.
 */
final class AospCronetImpl extends CronetImpl {
  @Override
  CronetSource getCronetSource() {
    return CronetSource.CRONET_SOURCE_PLATFORM;
  }
}
