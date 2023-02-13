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

import android.net.http.NetworkQualityRttListener;
import android.os.ConditionVariable;
import android.util.SparseIntArray;

import java.util.concurrent.Executor;

class TestNetworkQualityRttListener extends NetworkQualityRttListener {
    // Lock to ensure that observation counts can be updated and read by different threads.
    private final Object mLock = new Object();

    // Signals when the first RTT observation at the URL request layer is received.
    private final ConditionVariable mWaitForUrlRequestRtt = new ConditionVariable();

    private int mRttObservationCount;

    // Holds the RTT observations counts indexed by source.
    private SparseIntArray mRttObservationCountBySource = new SparseIntArray();

    private Thread mExecutorThread;

    /*
     * Constructs a NetworkQualityRttListener that can listen to the RTT observations at various
     * layers of the network stack.
     * @param executor The executor on which the observations are reported.
     */
    TestNetworkQualityRttListener(Executor executor) {
        super(executor);
    }

    @Override
    public void onRttObservation(int rttMs, long when, int source) {
        synchronized (mLock) {
            if (source == 0) {
                // Source 0 indicates that the RTT was observed at the URL request layer.
                mWaitForUrlRequestRtt.open();
            }

            mRttObservationCount++;
            mRttObservationCountBySource.put(source, mRttObservationCountBySource.get(source) + 1);

            if (mExecutorThread == null) {
                mExecutorThread = Thread.currentThread();
            }
            // Verify that the listener is always notified on the same thread.
            assertEquals(mExecutorThread, Thread.currentThread());
        }
    }

    /*
     * Blocks until the first RTT observation at the URL request layer is received.
     */
    public void waitUntilFirstUrlRequestRTTReceived() {
        mWaitForUrlRequestRtt.block();
    }

    public int rttObservationCount() {
        synchronized (mLock) {
            return mRttObservationCount;
        }
    }

    public int rttObservationCount(int source) {
        synchronized (mLock) {
            return mRttObservationCountBySource.get(source);
        }
    }

    public Thread getThread() {
        synchronized (mLock) {
            return mExecutorThread;
        }
    }
}