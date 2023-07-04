package android.net.http;

import org.chromium.net.CronetException;

class CronetExceptionWrapper extends HttpException {

    CronetExceptionWrapper(CronetException e) {
        super(e.getMessage(), ExceptionTranslationUtils.maybeTranslateException(e.getCause()));
    }
}
