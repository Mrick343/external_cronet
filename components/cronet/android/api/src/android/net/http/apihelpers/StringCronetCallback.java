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

import android.net.http.UrlResponseInfo;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * A specialization of {@link InMemoryTransformCronetCallback} that interprets the response body as
 * a string.
 *
 * <p>The charset used to decode the string is derived from the {@code Content-Type} header.
 */
public abstract class StringCronetCallback extends InMemoryTransformCronetCallback<String> {
    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";

    StringCronetCallback() {}

    @Override
    public StringCronetCallback addCompletionListener(
            CronetRequestCompletionListener<? super String> listener) {
        super.addCompletionListener(listener);
        return this;
    }

    @Override // Override to return the subtype
    protected String transformBodyBytes(UrlResponseInfo info, byte[] bodyBytes) {
        return new String(bodyBytes, getCharsetFromHeaders(info));
    }

    private Charset getCharsetFromHeaders(UrlResponseInfo info) {
        List<String> contentTypeHeaders = info.getAllHeaders().get(CONTENT_TYPE_HEADER_NAME);

        String charset = null;

        for (String header : contentTypeHeaders) {
            ContentTypeParametersParser parser = new ContentTypeParametersParser(header);

            while (parser.hasMore()) {
                Map.Entry<String, String> parameter;
                try {
                    parameter = parser.getNextParameter();
                } catch (ContentTypeParametersParser.ContentTypeParametersParserException e) {
                    // Malformed header, continue with the next one
                    break;
                }
                String parameterName = parameter.getKey();
                String parameterValue = parameter.getValue();

                if (charset != null && !parameterValue.equalsIgnoreCase(charset)) {
                    throw new IllegalArgumentException(
                            "Multiple charsets provided: " + parameterValue + " and " + charset);
                }
                charset = parameterValue;
            }
        }

        if (charset != null) {
            return Charset.forName(charset);
        } else {
            // Always UTF-8 on Android
            return Charset.defaultCharset();
        }
    }
}
