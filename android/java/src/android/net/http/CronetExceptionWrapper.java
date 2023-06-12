package android.net.http;

import android.net.http.HttpException;
import org.chromium.net.CronetException;

class CronetExceptionWrapper extends HttpException {

  CronetExceptionWrapper(CronetException e) {
    super(e.getMessage(), ExceptionTranslationUtils.translateNestedException(e.getCause()));
  }
}
