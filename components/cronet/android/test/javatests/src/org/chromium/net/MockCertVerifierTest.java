// Copyright 2015 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net;

import static com.google.common.truth.Truth.assertThat;

import static org.chromium.net.CronetTestRule.SERVER_CERT_PEM;
import static org.chromium.net.CronetTestRule.SERVER_KEY_PKCS8_PEM;
import static org.chromium.net.CronetTestRule.getContext;

<<<<<<< HEAD   (a4cf74 Merge remote-tracking branch 'aosp/master' into upstream-sta)
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
=======
import android.support.test.runner.AndroidJUnit4;

import androidx.test.filters.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.chromium.base.test.util.Feature;

/**
 * Unit tests for {@code MockCertVerifier}.
 */
@RunWith(AndroidJUnit4.class)
public class MockCertVerifierTest {
    private static final String TAG = MockCertVerifierTest.class.getSimpleName();

    @Rule
    public final CronetTestRule mTestRule = new CronetTestRule();

    private ExperimentalCronetEngine mCronetEngine;

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
    @Feature({"Cronet"})
    public void testRequest_failsWithoutMockVerifier() {
        ExperimentalCronetEngine.Builder builder =
                new ExperimentalCronetEngine.Builder(getContext());
        mCronetEngine = builder.build();

        String url = Http2TestServer.getEchoAllHeadersUrl();
        TestUrlRequestCallback callback = startAndWaitForComplete(url);
        assertThat(callback.mError).isNotNull();
        assertThat(callback.mError).hasMessageThat().contains("ERR_CERT_AUTHORITY_INVALID");
    }

    @Test
    @SmallTest
    @Feature({"Cronet"})
    public void testRequest_passesWithMockVerifier() {
        ExperimentalCronetEngine.Builder builder =
                new ExperimentalCronetEngine.Builder(getContext());
>>>>>>> BRANCH (14c906 Import Cronet version 108.0.5359.128)

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
