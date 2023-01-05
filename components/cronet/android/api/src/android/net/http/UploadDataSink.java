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

/**
 * Defines callbacks methods for {@link UploadDataProvider}. All methods
 * may be called synchronously or asynchronously, on any thread.
 */
public abstract class UploadDataSink {
    /**
     * Called by {@link UploadDataProvider} when a read succeeds.
     * @param finalChunk For chunked uploads, {@code true} if this is the final
     *     read. It must be {@code false} for non-chunked uploads.
     */
    public abstract void onReadSucceeded(boolean finalChunk);

    /**
     * Called by {@link UploadDataProvider} when a read fails.
     * @param exception Exception passed on to the embedder.
     */
    public abstract void onReadError(Exception exception);

    /**
     * Called by {@link UploadDataProvider} when a rewind succeeds.
     */
    public abstract void onRewindSucceeded();

    /**
     * Called by {@link UploadDataProvider} when a rewind fails, or if rewinding
     * uploads is not supported.
     * @param exception Exception passed on to the embedder.
     */
    public abstract void onRewindError(Exception exception);
}
