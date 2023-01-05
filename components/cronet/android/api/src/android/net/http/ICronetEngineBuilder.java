/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.net.http;

import java.util.Date;
import java.util.Set;

/**
 * Defines methods that the actual implementation of {@link CronetEngine.Builder} has to implement.
 * {@code CronetEngine.Builder} uses this interface to delegate the calls.
 * For the documentation of individual methods, please see the identically named methods in
 * {@link CronetEngine.Builder} and
 * {@link ExperimentalCronetEngine.Builder}.
 *
 * {@hide internal class}
 */
public abstract class ICronetEngineBuilder {
    // Public API methods.
    public abstract ICronetEngineBuilder addPublicKeyPins(String hostName, Set<byte[]> pinsSha256,
            boolean includeSubdomains, Date expirationDate);
    public abstract ICronetEngineBuilder addQuicHint(String host, int port, int alternatePort);
    public abstract ICronetEngineBuilder enableHttp2(boolean value);
    public abstract ICronetEngineBuilder enableHttpCache(int cacheMode, long maxSize);
    public abstract ICronetEngineBuilder enablePublicKeyPinningBypassForLocalTrustAnchors(
            boolean value);
    public abstract ICronetEngineBuilder enableQuic(boolean value);
    public abstract ICronetEngineBuilder enableSdch(boolean value);
    public ICronetEngineBuilder enableBrotli(boolean value) {
        // Do nothing for older implementations.
        return this;
    }
    public abstract ICronetEngineBuilder setExperimentalOptions(String options);
    public abstract ICronetEngineBuilder setStoragePath(String value);
    public abstract ICronetEngineBuilder setUserAgent(String userAgent);
    public abstract String getDefaultUserAgent();
    public abstract ExperimentalCronetEngine build();

    // Experimental API methods.
    //
    // Note: all experimental API methods should have default implementation. This will allow
    // removing the experimental methods from the implementation layer without breaking
    // the client.

    public ICronetEngineBuilder enableNetworkQualityEstimator(boolean value) {
        return this;
    }

    public ICronetEngineBuilder setThreadPriority(int priority) {
        return this;
    }
}
