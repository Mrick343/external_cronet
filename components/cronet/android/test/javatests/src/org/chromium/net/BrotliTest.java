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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.chromium.net.CronetTestRule.SERVER_CERT_PEM;
import static org.chromium.net.CronetTestRule.SERVER_KEY_PKCS8_PEM;
import static org.chromium.net.CronetTestRule.getContext;

import android.net.http.HttpEngine;
import android.net.http.ExperimentalHttpEngine;
import android.net.http.UrlRequest;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.chromium.net.CronetTestRule.OnlyRunNativeCronet;
import org.chromium.net.CronetTestRule.RequiresMinApi;

/**
 * Simple test for Brotli support.
 */
@RunWith(AndroidJUnit4.class)
@RequiresMinApi(5) // Brotli support added in API version 5: crrev.com/465216
public class BrotliTest {
    @Rule
    public final CronetTestRule mTestRule = new CronetTestRule();

    private HttpEngine mCronetEngine;

    @Before
    public void setUp() throws Exception {
        TestFilesInstaller.installIfNeeded(getContext());
        assertTrue(Http2TestServer.startHttp2TestServer(
                getContext(), SERVER_CERT_PEM, SERVER_KEY_PKCS8_PEM));
    }

    @After
    public void tearDown() throws Exception {
        assertTrue(Http2TestServer.shutdownHttp2TestServer());
        if (mCronetEngine != null) {
            mCronetEngine.shutdown();
        }
    }

    @Test
    @SmallTest
    @OnlyRunNativeCronet
    public void testBrotliAdvertised() throws Exception {
        ExperimentalHttpEngine.Builder builder =
                new ExperimentalHttpEngine.Builder(getContext());
        builder.setEnableBrotli(true);
        CronetTestUtil.setMockCertVerifierForTesting(
                builder, QuicTestServer.createMockCertVerifier());
        mCronetEngine = builder.build();
        String url = Http2TestServer.getEchoAllHeadersUrl();
        TestUrlRequestCallback callback = startAndWaitForComplete(url);
        assertEquals(200, callback.mResponseInfo.getHttpStatusCode());
        assertTrue(callback.mResponseAsString.contains("accept-encoding: gzip, deflate, br"));
    }

    @Test
    @SmallTest
    @OnlyRunNativeCronet
    public void testBrotliNotAdvertised() throws Exception {
        ExperimentalHttpEngine.Builder builder =
                new ExperimentalHttpEngine.Builder(getContext());
        CronetTestUtil.setMockCertVerifierForTesting(
                builder, QuicTestServer.createMockCertVerifier());
        mCronetEngine = builder.build();
        String url = Http2TestServer.getEchoAllHeadersUrl();
        TestUrlRequestCallback callback = startAndWaitForComplete(url);
        assertEquals(200, callback.mResponseInfo.getHttpStatusCode());
        assertFalse(callback.mResponseAsString.contains("br"));
    }

    @Test
    @SmallTest
    @OnlyRunNativeCronet
    public void testBrotliDecoded() throws Exception {
        ExperimentalHttpEngine.Builder builder =
                new ExperimentalHttpEngine.Builder(getContext());
        builder.setEnableBrotli(true);
        CronetTestUtil.setMockCertVerifierForTesting(
                builder, QuicTestServer.createMockCertVerifier());
        mCronetEngine = builder.build();
        String url = Http2TestServer.getServeSimpleBrotliResponse();
        TestUrlRequestCallback callback = startAndWaitForComplete(url);
        assertEquals(200, callback.mResponseInfo.getHttpStatusCode());
        String expectedResponse = "The quick brown fox jumps over the lazy dog";
        assertEquals(expectedResponse, callback.mResponseAsString);
        assertEquals(callback.mResponseInfo.getAllHeaders().get("content-encoding").get(0), "br");
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
