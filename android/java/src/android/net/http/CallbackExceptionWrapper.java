package android.net.http;

public class CallbackExceptionWrapper extends android.net.http.CallbackException {

    protected CallbackExceptionWrapper(org.chromium.net.CallbackException e) {
        // CallbackException guarantees that its cause will be the exception thrown during the
        // execution of the failed callback. Hence, we need to drop the received
        // org.chromium.net.CallbackException and link its cause to
        // android.net.http.CallbackException.
        super(e.getMessage(), ExceptionTranslationUtils.maybeTranslateException(e.getCause()));
    }
}
