package android.net.http;

import org.chromium.net.CronetException;

public class ExceptionTranslationUtils {
    static final String LOG = "ExceptionTranslationUtils";

    private ExceptionTranslationUtils() {}

    @SuppressWarnings("unchecked")
    public static <T, E extends Exception> T executeTranslatingExceptions(
            CronetWork<T, E> work, Class<E> exception) throws HttpException, E {
        try {
            return work.run();
        } catch (Exception e) {
            if (isUncheckedException(e)) {
                throw translateUncheckedException(e);
            }

            if (isCheckedException(e)) {
                throw translateCheckedException(e);
            }

            if (exception.isInstance(e)) {
                throw e;
            }

            throw new AssertionError("Unexpected exception", e);
        }
    }

    private static boolean isUncheckedException(Exception e) {
        return (e instanceof org.chromium.net.InlineExecutionProhibitedException);
    }

    private static boolean isCheckedException(Exception e) {
        return (e instanceof org.chromium.net.CronetException);
    }

    public static RuntimeException translateUncheckedException(Exception e) {
        if (!isUncheckedException(e)) {
            throw new IllegalArgumentException("Not an unchecked exception", e);
        }

        if (e instanceof org.chromium.net.InlineExecutionProhibitedException) {
            // InlineExecutionProhibitedException is final, so we can't wrap it.
            android.net.http.InlineExecutionProhibitedException translatedException =
                    new android.net.http.InlineExecutionProhibitedException();
            Throwable cause = e.getCause();
            translatedException.initCause(translateNestedException(cause));
            return translatedException;
        }

        throw new UnsupportedOperationException("Unchecked exception translation discrepancy", e);
    }

    public static HttpException translateCheckedException(Exception e) {
        if (!isCheckedException(e)) {
            throw new IllegalArgumentException("Not a checked exception", e);
        }

        if (e instanceof org.chromium.net.QuicException) {
            new QuicExceptionWrapper((org.chromium.net.QuicException) e);
        }

        if (e instanceof org.chromium.net.NetworkException) {
            return new NetworkExceptionWrapper((org.chromium.net.NetworkException) e);
        }

        if (e instanceof org.chromium.net.CallbackException) {
            return new CallbackExceptionWrapper((org.chromium.net.CallbackException) e);
        }

        if (e instanceof CronetException) {
            return new CronetExceptionWrapper((CronetException) e);
        }

        throw new UnsupportedOperationException("Checked exception translation discrepancy", e);
    }

    public static Throwable maybeTranslateException(Throwable t) {
        if (t == null) {
          return null;
        }
        if (!(t instanceof Exception)) {
          return t;
        }

        Exception e = (Exception) t;
        if (isUncheckedException(e)) {
           return translateUncheckedException(e);
        }
        if (isCheckedException(e)) {
           return translateCheckedException(e);
        }
        return t;
    }

    interface CronetWork<T, E extends Exception> {
        T run() throws E;
    }
}
