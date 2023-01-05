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

package android.net.http;

import java.util.concurrent.Executor;

/**
 * Listener that is notified of throughput observations from the network quality
 * estimator.
 * {@hide} as it's a prototype.
 */
public abstract class NetworkQualityThroughputListener {
    /**
     * The executor on which this listener will be notified. Set as a final
     * field, so it can be safely accessed across threads.
     */
    private final Executor mExecutor;

    /**
     * @param executor The executor on which the observations are reported.
     */
    public NetworkQualityThroughputListener(Executor executor) {
        if (executor == null) {
            throw new IllegalStateException("Executor must not be null");
        }
        mExecutor = executor;
    }

    public Executor getExecutor() {
        return mExecutor;
    }

    /**
     * Reports a new throughput observation.
     * @param throughputKbps the downstream throughput in kilobits per second.
     * @param whenMs milliseconds since the Epoch (January 1st 1970, 00:00:00.000).
     * @param source the observation source from {@link NetworkQualityObservationSource}.
     */
    public abstract void onThroughputObservation(int throughputKbps, long whenMs, int source);
}
