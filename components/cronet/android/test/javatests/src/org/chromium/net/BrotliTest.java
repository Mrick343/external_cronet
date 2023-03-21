// Copyright 2017 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.chromium.net.CronetTestRule.SERVER_CERT_PEM;
import static org.chromium.net.CronetTestRule.SERVER_KEY_PKCS8_PEM;
import static org.chromium.net.CronetTestRule.getContext;

<<<<<<< HEAD   (12482f Merge remote-tracking branch 'aosp/master' into upstream-sta)
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
=======
import android.support.test.runner.AndroidJUnit4;

import androidx.test.filters.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.chromium.base.test.util.Feature;
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

    private CronetEngine mCronetEngine;

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
    @Feature({"Cronet"})
    @OnlyRunNativeCronet
    public void testBrotliAdvertised() throws Exception {
        ExperimentalCronetEngine.Builder builder =
                new ExperimentalCronetEngine.Builder(getContext());
        builder.enableBrotli(true);
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
    @Feature({"Cronet"})
    @OnlyRunNativeCronet
    public void testBrotliNotAdvertised() throws Exception {
        ExperimentalCronetEngine.Builder builder =
                new ExperimentalCronetEngine.Builder(getContext());
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
    @Feature({"Cronet"})
    @OnlyRunNativeCronet
    public void testBrotliDecoded() throws Exception {
        ExperimentalCronetEngine.Builder builder =
                new ExperimentalCronetEngine.Builder(getContext());
        builder.enableBrotli(true);
>>>>>>> BRANCH (26b171 Part 2 of Import Cronet version 108.0.5359.128)
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
