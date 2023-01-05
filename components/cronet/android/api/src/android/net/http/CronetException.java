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

import java.io.IOException;

/**
 * Base exception passed to {@link UrlRequest.Callback#onFailed UrlRequest.Callback.onFailed()}.
 */
public class CronetException extends IOException {
    /**
     * Constructs an exception that is caused by {@code cause}.
     *
     * @param message explanation of failure.
     * @param cause the cause (which is saved for later retrieval by the {@link
     *         java.io.IOException#getCause getCause()} method). A null value is permitted, and
     *         indicates that the cause is nonexistent or unknown.
     */
    protected CronetException(String message, Throwable cause) {
        super(message, cause);
    }
}
