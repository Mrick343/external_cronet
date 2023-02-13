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

import static junit.framework.Assert.assertEquals;

import android.net.http.NetworkQualityThroughputListener;
import android.os.ConditionVariable;

import java.util.concurrent.Executor;

class TestNetworkQualityThroughputListener extends NetworkQualityThroughputListener {
    // Lock to ensure that observation counts can be updated and read by different threads.
    private final Object mLock = new Object();

    // Signals when the first throughput observation is received.
    private final ConditionVariable mWaitForThroughput = new ConditionVariable();

    private int mThroughputObservationCount;
    private Thread mExecutorThread;

    /*
     * Constructs a NetworkQualityThroughputListener that can listen to the throughput observations.
     * @param executor The executor on which the observations are reported.
     */
    TestNetworkQualityThroughputListener(Executor executor) {
        super(executor);
    }

    @Override
    public void onThroughputObservation(int throughputKbps, long when, int source) {
        synchronized (mLock) {
            mWaitForThroughput.open();
            mThroughputObservationCount++;
            if (mExecutorThread == null) {
                mExecutorThread = Thread.currentThread();
            }
            // Verify that the listener is always notified on the same thread.
            assertEquals(mExecutorThread, Thread.currentThread());
        }
    }

    /*
     * Blocks until the first throughput observation is received.
     */
    public void waitUntilFirstThroughputObservationReceived() {
        mWaitForThroughput.block();
    }

    public int throughputObservationCount() {
        synchronized (mLock) {
            return mThroughputObservationCount;
        }
    }

    public Thread getThread() {
        synchronized (mLock) {
            return mExecutorThread;
        }
    }
}