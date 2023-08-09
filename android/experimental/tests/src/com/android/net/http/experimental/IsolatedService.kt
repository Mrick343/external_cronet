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

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class EmptyIsolatedService : Service() {
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e(this::class.simpleName, "service: started isolated service")
        return 0
    }

    // disallow binding
    override fun onBind(intent: Intent): IBinder? = null
}

class EmptyService : Service() {
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e(this::class.simpleName, "service: started isolated service")
        doUrlRequest(this, "https://www.google.com/search?q=EmptyService")
        return 0
    }

    // disallow binding
    override fun onBind(intent: Intent): IBinder? = null
}

class EmptyAppZygoteIsolatedService : Service() {
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e(this::class.simpleName, "service: started isolated service")
        return 0
    }

    // disallow binding
    override fun onBind(intent: Intent): IBinder? = null
}

class EmptyAppZygoteIsolatedServiceCopy : Service() {
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e(this::class.simpleName, "service: started isolated service")
        return 0
    }

    // disallow binding
    override fun onBind(intent: Intent): IBinder? = null
}

