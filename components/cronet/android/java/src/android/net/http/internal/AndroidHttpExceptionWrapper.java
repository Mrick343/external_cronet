package android.net.http;

import android.net.http.HttpException;
import org.chromium.net.CronetException;

class AndroidHttpExceptionWrapper extends HttpException {

  AndroidHttpExceptionWrapper(CronetException delegate) {
    super(delegate.getMessage(), delegate);
  }
}