package android.net.http.wrapper;

import android.net.Network;

import org.chromium.net.ExperimentalCronetEngine;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandlerFactory;
import java.util.concurrent.Executor;

class CronetEngineWrapper extends HttpEngine {

    private final ExperimentalCronetEngine backend;

    public CronetEngineWrapper(ExperimentalCronetEngine backend) {
        this.backend = backend;
    }

    @Override
    public void shutdown() {
        backend.shutdown();
    }

    @Override
    public URLConnection openConnection(URL url) throws IOException {
        return CronetExceptionTranslationUtils.executeTranslatingExceptions(
                () -> backend.openConnection(url));
    }

    @Override
    public URLStreamHandlerFactory createUrlStreamHandlerFactory() {
        return backend.createURLStreamHandlerFactory();
    }

    @Override
    public void bindToNetwork(Network network) {
        long networkHandle = backend.UNBIND_NETWORK_HANDLE;
        if (network != null) {
            networkHandle = network.getNetworkHandle();
        }
        backend.bindToNetwork(networkHandle);
    }

    @Override
    public android.net.http.BidirectionalStream.Builder newBidirectionalStreamBuilder(
            String url, Executor executor, android.net.http.BidirectionalStream.Callback callback) {
        BidirectionalStreamCallbackWrapper wrappedCallback =
                new BidirectionalStreamCallbackWrapper(callback);
        return new BidirectionalStreamBuilderWrapper(
                backend.newBidirectionalStreamBuilder(url, wrappedCallback, executor));
    }

    @Override
    public android.net.http.UrlRequest.Builder newUrlRequestBuilder(
            String url, Executor executor, android.net.http.UrlRequest.Callback callback) {
        UrlRequestCallbackWrapper wrappedCallback = new UrlRequestCallbackWrapper(callback);
        return new UrlRequestBuilderWrapper(
                backend.newUrlRequestBuilder(url, wrappedCallback, executor));
    }
}
