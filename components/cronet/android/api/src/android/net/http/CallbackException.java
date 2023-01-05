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
 * Exception passed to {@link UrlRequest.Callback#onFailed UrlRequest.Callback.onFailed()} when
 * {@link UrlRequest.Callback} or {@link UploadDataProvider} method throws an exception. In this
 * case {@link java.io.IOException#getCause getCause()} can be used to find the thrown
 * exception.
 */
public abstract class CallbackException extends CronetException {
    /**
      * Constructs an exception that wraps {@code cause} thrown by a {@link UrlRequest.Callback}.
      *
      * @param message explanation of failure.
      * @param cause exception thrown by {@link UrlRequest.Callback} that's being wrapped. It is
      *        saved for later retrieval by the {@link java.io.IOException#getCause getCause()}.
      */
    protected CallbackException(String message, Throwable cause) {
        super(message, cause);
    }
}
