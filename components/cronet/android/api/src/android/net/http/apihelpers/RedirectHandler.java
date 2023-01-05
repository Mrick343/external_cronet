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

/**
 * An interface for classes specifying how Cronet should behave on redirects.
 */
public interface RedirectHandler {
    /**
     * Returns whether the redirect should be followed.
     *
     * @param info the response info of the redirect response
     * @param newLocationUrl the redirect location
     * @return whether Cronet should follow teh redirect or not
     */
    boolean shouldFollowRedirect(UrlResponseInfo info, String newLocationUrl) throws Exception;
}
