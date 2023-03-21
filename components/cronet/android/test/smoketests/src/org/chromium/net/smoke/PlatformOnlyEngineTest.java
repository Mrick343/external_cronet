// Copyright 2016 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net.smoke;

import static org.chromium.net.smoke.CronetSmokeTestRule.assertJavaEngine;
import static org.chromium.net.smoke.CronetSmokeTestRule.assertSuccessfulNonEmptyResponse;

<<<<<<< HEAD   (12482f Merge remote-tracking branch 'aosp/master' into upstream-sta)

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.chromium.net.UrlRequest;

/**
 * Tests scenario when an app doesn't contain the native Cronet implementation.
 */
@RunWith(AndroidJUnit4.class)
public class PlatformOnlyEngineTest {
    private String mURL;
    private TestSupport.TestServer mServer;

    @Rule
    public CronetSmokeTestRule mRule = new CronetSmokeTestRule();

    @Before
    public void setUp() throws Exception {
        // Java-only implementation of the Cronet engine only supports Http/1 protocol.
        mServer = mRule.getTestSupport().createTestServer(
                ApplicationProvider.getApplicationContext(), TestSupport.Protocol.HTTP1);
=======
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import androidx.test.filters.SmallTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.chromium.net.UrlRequest;

/**
 * Tests scenario when an app doesn't contain the native Cronet implementation.
 */
@RunWith(AndroidJUnit4.class)
public class PlatformOnlyEngineTest {
    private String mURL;
    private TestSupport.TestServer mServer;

    @Rule
    public CronetSmokeTestRule mRule = new CronetSmokeTestRule();

    @Before
    public void setUp() throws Exception {
        // Java-only implementation of the Cronet engine only supports Http/1 protocol.
        mServer = mRule.getTestSupport().createTestServer(
                InstrumentationRegistry.getTargetContext(), TestSupport.Protocol.HTTP1);
>>>>>>> BRANCH (26b171 Part 2 of Import Cronet version 108.0.5359.128)
        Assert.assertTrue(mServer.start());
        mURL = mServer.getSuccessURL();
    }

    @After
    public void tearDown() throws Exception {
        mServer.shutdown();
    }

    /**
     * Test a successful response when a request is sent by the Java Cronet Engine.
     */
    @Test
    @SmallTest
    public void testSuccessfulResponse() {
        mRule.initCronetEngine();
        assertJavaEngine(mRule.getCronetEngine());
        SmokeTestRequestCallback callback = new SmokeTestRequestCallback();
        UrlRequest.Builder requestBuilder = mRule.getCronetEngine().newUrlRequestBuilder(
                mURL, callback, callback.getExecutor());
        requestBuilder.build().start();
        callback.blockForDone();
        assertSuccessfulNonEmptyResponse(callback, mURL);
    }
}
