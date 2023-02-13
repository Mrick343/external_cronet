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

import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.test.util.UrlUtils;

/**
 * A Java wrapper to supply a net::MockCertVerifier which can be then passed
 * into {@link CronetEngine.Builder#setMockCertVerifierForTesting}.
 * The native pointer will be freed when the CronetEngine is torn down.
 */
@JNINamespace("cronet")
public class MockCertVerifier {
    private MockCertVerifier() {}

    /**
     * Creates a new net::MockCertVerifier, and returns a pointer to it.
     * @param certs a String array of certificate filenames in
     *         net::GetTestCertsDirectory() to accept in testing.
     * @return a pointer to the newly created net::MockCertVerifier.
     */
    public static long createMockCertVerifier(String[] certs, boolean knownRoot) {
//        return nativeCreateMockCertVerifier(certs, knownRoot, UrlUtils.getIsolatedTestRoot());
         return nativeCreateMockCertVerifier(certs, knownRoot, "/data/user/0/android.net.http.mts/cache/cronet_test_data");

    }

    /**
     * Creates a new free-for-all net::MockCertVerifier and returns a pointer to it.
     *
     * @return a pointer to the newly created net::MockCertVerifier.
     */
    public static long createFreeForAllMockCertVerifier() {
        return nativeCreateFreeForAllMockCertVerifier();
    }

    private static native long nativeCreateMockCertVerifier(
            String[] certs, boolean knownRoot, String testDataDir);

    private static native long nativeCreateFreeForAllMockCertVerifier();
}
