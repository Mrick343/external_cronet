package android.net.http;

import android.net.http.HttpException;
import org.chromium.net.CronetException;

public class CronetExceptionTranslationUtils {
  final static String LOG = "CronetExceptionTranslationUtils";

  @SuppressWarnings("unchecked")
  public static <T, E extends Exception> T executeTranslatingCronetExceptions(
      CronetWork<T, E> work, Class<E> nonCronetException)
      throws HttpException, E {
    try {
      return work.run();
    } catch (Exception e) {
      if (isUncheckedAndroidCronetException(e)) {
        throw translateUncheckedAndroidCronetException(e);
      }

      if (isCheckedAndroidCronetException(e)) {
        throw translateCheckedAndroidCronetException(e);
      }

      if (nonCronetException.isInstance(e)) {
        throw e;
      }

      throw new AssertionError("Unexpected exception", e);
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
      Throwable cause = e.getCause();
      wrappedException.initCause(translateNestedException(cause));
      return wrappedException;
    }

    throw new UnsupportedOperationException("Unchecked exception translation discrepancy", e);
  }

  public static HttpException translateCheckedAndroidCronetException(Exception e) {
    if (!isCheckedAndroidCronetException(e)) {
      throw new IllegalArgumentException("Not an Android Cronet exception", e);
    }

    if (e instanceof org.chromium.net.QuicException) {
      new AndroidQuicExceptionWrapper((org.chromium.net.QuicException) e);
    }

    if (e instanceof org.chromium.net.NetworkException) {
      return new AndroidNetworkExceptionWrapper((org.chromium.net.NetworkException) e);
    }

    if (e instanceof org.chromium.net.CallbackException) {
      return new AndroidCallbackExceptionWrapper((org.chromium.net.CallbackException) e);
    }

    if (e instanceof CronetException) {
      return new AndroidHttpExceptionWrapper((CronetException) e);
    }

    throw new UnsupportedOperationException("Checked exception translation discrepancy", e);
  }

  public static Throwable translateNestedException(Throwable t) {
    if (t instanceof Exception) {
      Exception e = (Exception) t;
    if (isUncheckedAndroidCronetException(e)) {
      return translateUncheckedAndroidCronetException(e);
    }
    if (isCheckedAndroidCronetException(e)) {
      return translateCheckedAndroidCronetException(e);
    }
    }
      return t;
  }

  interface CronetWork<T, E extends Exception> {
    T run() throws E;
  }
}
