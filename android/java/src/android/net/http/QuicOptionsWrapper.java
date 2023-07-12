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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;

public class QuicOptionsWrapper extends org.chromium.net.QuicOptions {
    private final android.net.http.QuicOptions backend;

    QuicOptionsWrapper(android.net.http.QuicOptions backend) {
        super(org.chromium.net.QuicOptions.builder());
        this.backend = backend;
    }

    @Override
    public Set<String> getQuicHostAllowlist() {
        return backend.getAllowedQuicHosts();
    }

    @Override
    public Integer getInMemoryServerConfigsCacheSize() {
        if (!backend.hasInMemoryServerConfigsCacheSize()) {
          return null;
        }
        return backend.getInMemoryServerConfigsCacheSize();
    }

    @Override
    @Nullable
    public String getHandshakeUserAgent() {
        return backend.getHandshakeUserAgent();
    }

    @Override
    @Nullable
    public Long getIdleConnectionTimeoutSeconds() {
        Duration connectionTimeout = backend.getIdleConnectionTimeout();
        if (connectionTimeout == null) {
            return null;
        }
        return connectionTimeout.getSeconds();
    }
}
