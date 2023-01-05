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

import org.json.JSONException;
import org.json.JSONObject;

import android.net.http.UrlResponseInfo;

/**
 * A specialization of {@link InMemoryTransformCronetCallback} that interprets the response body as
 * JSON.
 */
public abstract class JsonCronetCallback extends InMemoryTransformCronetCallback<JSONObject> {
    private static final StringCronetCallback STRING_CALLBACK = new StringCronetCallback() {
        @Override
        protected boolean shouldFollowRedirect(UrlResponseInfo info, String newLocationUrl) {
            throw new UnsupportedOperationException();
        }
    };

    @Override // Override to return the subtype
    public JsonCronetCallback addCompletionListener(
            CronetRequestCompletionListener<? super JSONObject> listener) {
        super.addCompletionListener(listener);
        return this;
    }

    @Override
    protected JSONObject transformBodyBytes(UrlResponseInfo info, byte[] bodyBytes) {
        String bodyString = STRING_CALLBACK.transformBodyBytes(info, bodyBytes);
        try {
            return new JSONObject(bodyString);
        } catch (JSONException e) {
            // As suggested by JSONException javadoc
            throw new IllegalArgumentException("Cannot parse the string as JSON!", e);
        }
    }
}
