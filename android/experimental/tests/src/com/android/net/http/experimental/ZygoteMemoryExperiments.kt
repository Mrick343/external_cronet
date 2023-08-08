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

package com.android.net.http.experimental

import android.content.Intent
import java.nio.ByteBuffer;
import android.net.http.HttpEngine
import java.util.concurrent.Executors
import android.net.http.UrlRequest
import android.net.http.HttpException
import android.net.http.UrlResponseInfo
import android.content.ComponentName
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.android.compatibility.common.util.SystemUtil.runShellCommand
import kotlin.test.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith

private const val TAG = "ZygoteMemoryExperiments"

/**
 * Not an actual test but a convenient utility to run different Zygote memory experiments.
 */
@RunWith(AndroidJUnit4::class)
class ZygoteMemoryExperiments {
    @get:Rule val testName = TestName()

    private val context by lazy { InstrumentationRegistry.getInstrumentation().context }

    private fun startPerfettoTrace() {
        // Clean up output after previous test run.
        val perfettoConfig = "/data/misc/perfetto-traces/perfetto-config.txt"
        val traceOut = "/data/misc/perfetto-traces/trace-${testName.methodName}"
        runShellCommand("perfetto -D -c $perfettoConfig --txt -o $traceOut")
    }

    @Before
    fun setUp() {
        startPerfettoTrace()
    }

    @After
    fun tearDown() {
        // HACK: Wait for trace to complete
        Thread.sleep(7000)
    }

    @Test
    fun testIsolatedService() {
        val serviceIntent = Intent(context, EmptyIsolatedService::class.java)
        context.startService(serviceIntent)
        val mEngineBuilder = HttpEngine.Builder(context);
        val mEngine = mEngineBuilder.build()
        val mCallback = MyCallback()
        val builder =
                mEngine.newUrlRequestBuilder("www.google.com", Executors.newSingleThreadExecutor(), mCallback)
        val mRequest = builder.build()
        mRequest.start()
    }

    @Test
    fun testAppZygoteIsolatedService() {
        val serviceIntent = Intent(context, EmptyAppZygoteIsolatedService::class.java)
        context.startService(serviceIntent)
    }

    @Test
    fun testMultipleAppZygoteIsolatedServices() {
        val serviceIntent = Intent(context, EmptyAppZygoteIsolatedService::class.java)
        context.startService(serviceIntent)
        val serviceIntent2 = Intent(context, EmptyAppZygoteIsolatedServiceCopy::class.java)
        context.startService(serviceIntent2)
    }
}


class MyCallback : UrlRequest.Callback {
        override fun onRedirectReceived(
                request: UrlRequest, info: UrlResponseInfo, newLocationUrl: String) {
          Log.i(TAG, "onRedirectReceived")
          request.followRedirect()
        }


        override fun onResponseStarted(request: UrlRequest, info: UrlResponseInfo) {
          Log.i(TAG, "onResponseStarted")
          request.read(ByteBuffer.allocateDirect(1024))
        }

        override fun onReadCompleted(
                request: UrlRequest, info: UrlResponseInfo, byteBuffer: ByteBuffer) {
          Log.i(TAG, "onReadCompleted")
          request.read(ByteBuffer.allocateDirect(1024))
        }

        override fun onSucceeded(request: UrlRequest, info: UrlResponseInfo) {
          Log.i(TAG, "onReadCompleted")
        }

        override fun onFailed(
                request: UrlRequest, info: UrlResponseInfo?, error: HttpException) {
          Log.i(TAG, "onFailed")
        }

        override fun onCanceled(request: UrlRequest, info: UrlResponseInfo?) {
          Log.i(TAG, "onCanceled")
        }
    }

