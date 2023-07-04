package android.net.http;

class UploadDataSinkWrapper extends android.net.http.UploadDataSink {

    private final org.chromium.net.UploadDataSink backend;

    public UploadDataSinkWrapper(org.chromium.net.UploadDataSink backend) {
        this.backend = backend;
    }

    @Override
    public void onReadSucceeded(boolean finalChunk) {
        backend.onReadSucceeded(finalChunk);
    }

    @Override
    public void onReadError(Exception exception) {
        backend.onReadError(exception);
    }

    @Override
    public void onRewindSucceeded() {
        backend.onRewindSucceeded();
    }

    @Override
    public void onRewindError(Exception exception) {
        backend.onRewindError(exception);
    }
}
