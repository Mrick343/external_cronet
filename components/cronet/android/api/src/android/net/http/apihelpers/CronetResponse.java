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

package android.net.http.apihelpers;

import androidx.annotation.Nullable;

import android.net.http.UrlResponseInfo;

import java.util.Objects;

/**
 * A helper object encompassing the headers, body and metadata of a response to Cronet URL requests.
 *
 * @param <T> the response body type
 */
public class CronetResponse<T> {
    /** The headers and other metadata of the response. */
    private final UrlResponseInfo mUrlResponseInfo;
    /** The full body of the response, after performing a user-defined deserialization. */
    private final @Nullable T mResponseBody;

    CronetResponse(UrlResponseInfo urlResponseInfo, @Nullable T responseBody) {
        this.mUrlResponseInfo = urlResponseInfo;
        this.mResponseBody = responseBody;
    }

    /** Returns the headers and other metadata of the response. */
    public UrlResponseInfo getUrlResponseInfo() {
        return mUrlResponseInfo;
    }

    /** Returns the full body of the response, after performing a user-defined deserialization. */
    public @Nullable T getResponseBody() {
        return mResponseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CronetResponse)) return false;
        CronetResponse<?> that = (CronetResponse<?>) o;
        return Objects.equals(mUrlResponseInfo, that.mUrlResponseInfo)
                && Objects.equals(mResponseBody, that.mResponseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUrlResponseInfo, mResponseBody);
    }
}
