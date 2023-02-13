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

package org.chromium.net;

import static com.google.common.truth.Truth.assertThat;

import static org.chromium.net.CronetTestRule.SERVER_CERT_PEM;
import static org.chromium.net.CronetTestRule.SERVER_KEY_PKCS8_PEM;
import static org.chromium.net.CronetTestRule.getContext;

import android.net.http.ExperimentalHttpEngine;
import android.net.http.UrlRequest;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit tests for {@code MockCertVerifier}.
 */
@RunWith(AndroidJUnit4.class)
public class MockCertVerifierTest {
    private static final String TAG = MockCertVerifierTest.class.getSimpleName();

    @Rule
    public final CronetTestRule mTestRule = new CronetTestRule();

    private ExperimentalHttpEngine mCronetEngine;

    @Before
    public void setUp() throws Exception {
        // Load library first to create MockCertVerifier.
        System.loadLibrary("cronet_tests");

        assertThat(Http2TestServer.startHttp2TestServer(
                getContext(), SERVER_CERT_PEM, SERVER_KEY_PKCS8_PEM)).isTrue();
    }

    @After
    public void tearDown() throws Exception {
        assertThat(Http2TestServer.shutdownHttp2TestServer()).isTrue();
        if (mCronetEngine != null) {
            mCronetEngine.shutdown();
        }
    }

    @Test
    @SmallTest
    public void testRequest_failsWithoutMockVerifier() {
        ExperimentalHttpEngine.Builder builder =
                new ExperimentalHttpEngine.Builder(getContext());
        mCronetEngine = builder.build();

        String url = Http2TestServer.getEchoAllHeadersUrl();
        TestUrlRequestCallback callback = startAndWaitForComplete(url);
        assertThat(callback.mError).isNotNull();
        assertThat(callback.mError).hasMessageThat().contains("ERR_CERT_AUTHORITY_INVALID");
    }

    @Test
    @SmallTest
    public void testRequest_passesWithMockVerifier() {
        ExperimentalHttpEngine.Builder builder =
                new ExperimentalHttpEngine.Builder(getContext());

        CronetTestUtil.setMockCertVerifierForTesting(
                builder, MockCertVerifier.createFreeForAllMockCertVerifier());
        mCronetEngine = builder.build();

        String url = Http2TestServer.getEchoAllHeadersUrl();
        TestUrlRequestCallback callback = startAndWaitForComplete(url);
        assertThat(callback.mResponseInfo.getHttpStatusCode()).isEqualTo(200);
    }

    private TestUrlRequestCallback startAndWaitForComplete(String url) {
        TestUrlRequestCallback callback = new TestUrlRequestCallback();
        UrlRequest.Builder builder =
                mCronetEngine.newUrlRequestBuilder(url, callback, callback.getExecutor());
        builder.build().start();
        callback.blockForDone();
        return callback;
    }
}
