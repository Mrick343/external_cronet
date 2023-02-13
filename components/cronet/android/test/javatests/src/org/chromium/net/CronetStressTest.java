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
import static org.junit.Assert.assertTrue;

import static org.chromium.net.CronetTestRule.getContext;

import android.net.http.UrlRequest;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import android.net.http.apihelpers.UploadDataProviders;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.chromium.net.CronetTestRule.CronetTestFramework;
import org.chromium.net.CronetTestRule.OnlyRunNativeCronet;

/**
 * Tests that making a large number of requests do not lead to crashes.
 */
@RunWith(AndroidJUnit4.class)
public class CronetStressTest {
    @Rule
    public final CronetTestRule mTestRule = new CronetTestRule();
    private CronetTestFramework mTestFramework;

    @Before
    public void setUp() throws Exception {
        mTestFramework = mTestRule.startCronetTestFramework();
        assertTrue(NativeTestServer.startNativeTestServer(getContext()));
    }

    @After
    public void tearDown() throws Exception {
        NativeTestServer.shutdownNativeTestServer();
        mTestFramework.mCronetEngine.shutdown();
    }

    @Test
    @LargeTest
    @OnlyRunNativeCronet
    public void testLargeNumberOfUploads() throws Exception {
        final int kNumRequest = 1000;
        final int kNumRequestHeaders = 100;
        final int kNumUploadBytes = 1000;
        final byte[] b = new byte[kNumUploadBytes];
        for (int i = 0; i < kNumRequest; i++) {
            TestUrlRequestCallback callback = new TestUrlRequestCallback();
            UrlRequest.Builder builder = mTestFramework.mCronetEngine.newUrlRequestBuilder(
                    NativeTestServer.getEchoAllHeadersURL(), callback.getExecutor(), callback);
            for (int j = 0; j < kNumRequestHeaders; j++) {
                builder.addHeader("header" + j, Integer.toString(j));
            }
            builder.addHeader("content-type", "useless/string");
            builder.setUploadDataProvider(
                    UploadDataProviders.create(b, 0, kNumUploadBytes), callback.getExecutor());
            UrlRequest request = builder.build();
            request.start();
            callback.blockForDone();
            callback.shutdownExecutor();
            assertEquals(200, callback.mResponseInfo.getHttpStatusCode());
        }
    }
}
