package android.net.http.wrapper;

public class NetworkExceptionWrapper extends android.net.http.NetworkException {

    private final org.chromium.net.NetworkException backend;

    NetworkExceptionWrapper(org.chromium.net.NetworkException backend) {
        this(backend, false);
    }

    NetworkExceptionWrapper(
            org.chromium.net.NetworkException backend, boolean expectQuicException) {
        super(backend.getMessage(), backend);
        this.backend = backend;

        if (!expectQuicException && backend instanceof org.chromium.net.QuicException) {
            throw new IllegalArgumentException(
                    "Translating QuicException as NetworkException results in loss of information."
                        + " Make sure you handle QuicException first. See the stacktrace for where"
                        + " the translation is being performed, and the cause for the exception"
                        + " being translated.");
        }
    }

    @Override
    public int getErrorCode() {
        return backend.getErrorCode();
    }

    @Override
    public boolean isImmediatelyRetryable() {
        return backend.immediatelyRetryable();
    }
}
