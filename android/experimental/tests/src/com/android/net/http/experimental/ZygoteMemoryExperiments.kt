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
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.android.compatibility.common.util.SystemUtil.runShellCommand
import kotlin.test.assertNotNull
import org.junit.Test

private const val TAG = "ZygoteMemoryExperiments"

/**
 * Not an actual test but a convenient utility to run different Zygote memory experiments.
 */
@SmallTest
class ZygoteMemoryExperiments {
    private val context by lazy { InstrumentationRegistry.getInstrumentation().context }

    private fun logMeminfo(test: String, componentName: ComponentName) {
        assertNotNull(componentName, "Did the service failed to start?")
        val serviceName = "${componentName.packageName}:Http:${componentName.className}"
        val result = runShellCommand("dumpsys meminfo -d $serviceName")
        Log.i(TAG, "$test: $result")
    }

    @Test
    fun testIsolatedService() {
        Log.i(TAG, "start isolated service")
        val serviceIntent = Intent(context, EmptyIsolatedService::class.java)
        val componentName = context.startService(serviceIntent)

        // TODO: don't sleep and use bindService() instead.
        Thread.sleep(2000)
        logMeminfo("testIsolatedService", componentName)
    }
}

