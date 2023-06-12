package android.net.http;

import android.net.Network;

import org.chromium.net.ExperimentalCronetEngine;

import java.util.concurrent.Executor;

public class UrlRequestBuilderWrapper extends android.net.http.UrlRequest.Builder {

    private final org.chromium.net.ExperimentalUrlRequest.Builder backend;

    public UrlRequestBuilderWrapper(org.chromium.net.ExperimentalUrlRequest.Builder backend) {
        this.backend = backend;
    }

    @Override
    public android.net.http.UrlRequest.Builder setHttpMethod(String method) {
        backend.setHttpMethod(method);
        return this;
    }

    @Override
    public android.net.http.UrlRequest.Builder addHeader(String header, String value) {
        backend.addHeader(header, value);
        return this;
    }

    @Override
    public android.net.http.UrlRequest.Builder setCacheDisabled(boolean disableCache) {
        backend.disableCache();
        return this;
    }

    @Override
    public android.net.http.UrlRequest.Builder setPriority(int priority) {
        backend.setPriority(priority);
        return this;
    }

    @Override
    public android.net.http.UrlRequest.Builder setUploadDataProvider(
            android.net.http.UploadDataProvider provider, Executor executor) {
        UploadDataProviderWrapper wrappedProvider = new UploadDataProviderWrapper(provider);
        backend.setUploadDataProvider(provider, executor);
        return this;
    }

    @Override
    public android.net.http.UrlRequest.Builder setDirectExecutorAllowed(
            boolean allowDirectExecutor) {
        backend.allowDirectExecutor();
        return this;
    }

    @Override
    public android.net.http.UrlRequest.Builder bindToNetwork(Network network) {
        long networkHandle = ExperimentalCronetEngine.UNBIND_NETWORK_HANDLE;
        if (network != null) {
            networkHandle = network.getNetworkHandle();
        }
        backend.bindToNetwork(networkHandle);
        return this;
    }

    @Override
    public android.net.http.UrlRequest.Builder setTrafficStatsUid(int uid) {
        backend.setTrafficStatsUid(uid);
        return this;
    }

    @Override
    public android.net.http.UrlRequest.Builder setTrafficStatsTag(int tag) {
        backend.setTrafficStatsTag(tag);
        return this;
    }

    @Override
    public android.net.http.UrlRequest build() {
        return new UrlRequestWrapper(backend.build());
    }
}
