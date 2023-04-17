package android.net.http;

import android.net.http.HttpException;
import org.chromium.net.CronetException;

public class CronetExceptionTranslationUtils {

  @SuppressWarnings("unchecked")
  public static <T, E extends Exception> T executeTranslatingCronetExceptions(
      CronetWork<T, E> work, Class<E> nonCronetException)
      throws HttpException, E {
    try {
      return work.run();
    } catch (Exception e) {
      if (isUncheckedAndroidCronetException(e)) {
        throw translateUncheckedAndroidCronetException(e);
      } else if (isCheckedAndroidCronetException(e)) {
        throw translateCheckedAndroidCronetException(e);
      } else if (nonCronetException.isInstance(e)) {
        throw (E) e;
      } else {
        throw new AssertionError("Unexpected exception", e);
      }
    }
  }

  public static boolean isUncheckedAndroidCronetException(Exception e) {
    return (e instanceof org.chromium.net.InlineExecutionProhibitedException);
  }

  public static boolean isCheckedAndroidCronetException(Exception e) {
    return (e instanceof org.chromium.net.CronetException);
  }

  public static RuntimeException translateUncheckedAndroidCronetException(Exception e) {
    if (!isUncheckedAndroidCronetException(e)) {
      throw new IllegalArgumentException("Not an Android Cronet exception", e);
    }

    if (e instanceof org.chromium.net.InlineExecutionProhibitedException) {
      // InlineExecutionProhibitedException is final so we can't have our own flavor
      android.net.http.InlineExecutionProhibitedException wrappedException =
          new android.net.http.InlineExecutionProhibitedException();
      wrappedException.initCause(e);
      return wrappedException;
    }

    throw new UnsupportedOperationException("Unchecked exception translation discrepancy", e);
  }

  public static HttpException translateCheckedAndroidCronetException(Exception e) {
    if (!isCheckedAndroidCronetException(e)) {
      throw new IllegalArgumentException("Not an Android Cronet exception", e);
    }

    if (e instanceof org.chromium.net.QuicException) {
      return new AndroidQuicExceptionWrapper((org.chromium.net.QuicException) e);
    } else if (e instanceof org.chromium.net.NetworkException) {
      return new AndroidNetworkExceptionWrapper((org.chromium.net.NetworkException) e);
    } else if (e instanceof org.chromium.net.CallbackException) {
      return new AndroidCallbackExceptionWrapper((org.chromium.net.CallbackException) e);
    } else if (e instanceof CronetException) {
      return new AndroidHttpExceptionWrapper((CronetException) e);
    }

    throw new UnsupportedOperationException("Checked exception translation discrepancy", e);
  }

  interface CronetWork<T, E extends Exception> {
    T run() throws E;
  }
}