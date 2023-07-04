package android.net.http;

import org.chromium.net.CronetException;

import java.io.IOException;

public class CronetExceptionTranslationUtils {
    private CronetExceptionTranslationUtils() {}

    public interface CronetRunnable<T> {
        T run() throws IOException;
    }

    public static <T> T executeTranslatingExceptions(
            CronetRunnable<T> work) throws IOException {
        try {
            return work.run();
        } catch (CronetException e) {
            throw translateException(e);
        }
    }

    public static HttpException translateException(CronetException e) {
        if (e instanceof org.chromium.net.QuicException) {
            return new QuicExceptionWrapper((org.chromium.net.QuicException) e);
        }

        if (e instanceof org.chromium.net.NetworkException) {
            return new NetworkExceptionWrapper((org.chromium.net.NetworkException) e);
        }

        if (e instanceof org.chromium.net.CallbackException) {
            return new CallbackExceptionWrapper((org.chromium.net.CallbackException) e);
        }

        return new CronetExceptionWrapper(e);
    }

    public static Throwable maybeTranslateException(Throwable t) {
        if (t instanceof org.chromium.net.InlineExecutionProhibitedException) {
            // InlineExecutionProhibitedException is final, so we can't wrap it.
            android.net.http.InlineExecutionProhibitedException translatedException =
                new android.net.http.InlineExecutionProhibitedException();
            translatedException.initCause(t);
            return translatedException;
        }

        return t;
   }
}
