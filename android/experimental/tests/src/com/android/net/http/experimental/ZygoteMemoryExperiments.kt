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
import android.content.ComponentName
import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.android.compatibility.common.util.SystemUtil.runShellCommand
import kotlin.test.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val TAG = "ZygoteMemoryExperiments"

/**
 * Not an actual test but a convenient utility to run different Zygote memory experiments.
 */
@RunWith(AndroidJUnit4::class)
class ZygoteMemoryExperiments {
    private val context by lazy { InstrumentationRegistry.getInstrumentation().context }

    private fun startPerfettoTrace() {
        // Clean up output after previous test run.
        val perfettoConfig = "/data/misc/perfetto-traces/perfetto-config.txt"
        val traceOut = "/data/misc/perfetto-traces/trace"
        runShellCommand("rm $traceOut")
        runShellCommand("perfetto -c $perfettoConfig --txt -o $traceOut")
    }

    @Before
    fun setUp() {
        startPerfettoTrace()
    }

    @After
    fun tearDown() {
        // HACK: Wait for trace to complete
        Thread.sleep(5000)
    }

    @Test
    fun testIsolatedService() {
        val serviceIntent = Intent(context, EmptyIsolatedService::class.java)
        context.startService(serviceIntent)
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

