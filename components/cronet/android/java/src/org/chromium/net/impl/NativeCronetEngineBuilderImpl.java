// Copyright 2017 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net.impl;

import android.content.Context;

<<<<<<< HEAD   (12482f Merge remote-tracking branch 'aosp/master' into upstream-sta)
import android.net.http.ExperimentalHttpEngine;
import android.net.http.IHttpEngineBuilder;

/**
 * Implementation of {@link IHttpEngineBuilder} that builds native Cronet engine.
 */
public class NativeCronetEngineBuilderImpl extends CronetEngineBuilderImpl {
    /**
     * Builder for Native Cronet Engine.
     * Default config enables SPDY, disables QUIC and HTTP cache.
     *
     * @param context Android {@link Context} for engine to use.
     */
    public NativeCronetEngineBuilderImpl(Context context) {
        super(context);
    }

    @Override
    public ExperimentalHttpEngine build() {
        if (getUserAgent() == null) {
            setUserAgent(getDefaultUserAgent());
        }

        ExperimentalHttpEngine builder = new CronetUrlRequestContext(this);
=======
import org.chromium.net.ExperimentalCronetEngine;
import org.chromium.net.ICronetEngineBuilder;

/**
 * Implementation of {@link ICronetEngineBuilder} that builds native Cronet engine.
 */
public class NativeCronetEngineBuilderImpl extends CronetEngineBuilderImpl {
    /**
     * Builder for Native Cronet Engine.
     * Default config enables SPDY, disables QUIC and HTTP cache.
     *
     * @param context Android {@link Context} for engine to use.
     */
    public NativeCronetEngineBuilderImpl(Context context) {
        super(context);
    }

    @Override
    public ExperimentalCronetEngine build() {
        if (getUserAgent() == null) {
            setUserAgent(getDefaultUserAgent());
        }

        ExperimentalCronetEngine builder = new CronetUrlRequestContext(this);
>>>>>>> BRANCH (26b171 Part 2 of Import Cronet version 108.0.5359.128)

        // Clear MOCK_CERT_VERIFIER reference if there is any, since
        // the ownership has been transferred to the engine.
        mMockCertVerifier = 0;

        return builder;
    }
}
