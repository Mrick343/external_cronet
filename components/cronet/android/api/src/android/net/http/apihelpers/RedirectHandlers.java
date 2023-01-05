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

/** Utility class for standard {@link RedirectHandler} implementations. * */
public class RedirectHandlers {
    /** Returns a redirect handler that never follows redirects. */
    public static RedirectHandler neverFollow() {
        return (info, newLocationUrl) -> false;
    }

    /**
     * Returns a redirect handler that always follows redirects.
     *
     * <p>Note that the maximum number of redirects to follow is still limited internally to prevent
     * infinite looping.
     */
    public static RedirectHandler alwaysFollow() {
        return (info, newLocationUrl) -> true;
    }

    private RedirectHandlers() {}
}
