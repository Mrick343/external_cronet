package android.net.http;

import android.net.http.HttpException;
import org.chromium.net.CronetException;
import android.util.Log;

class AndroidHttpExceptionWrapper extends HttpException {

  AndroidHttpExceptionWrapper(CronetException delegate) {
    super(delegate.getMessage(), CronetExceptionTranslationUtils.translateNestedException(delegate.getCause()));
  }
}
