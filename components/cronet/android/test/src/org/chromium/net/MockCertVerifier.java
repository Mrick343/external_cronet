// Copyright 2015 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net;

import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.JNINamespace;
<<<<<<< HEAD   (8c5f24 cronet: update METADATA to version 110)
=======
import org.chromium.base.annotations.NativeMethods;
import org.chromium.base.test.util.UrlUtils;
>>>>>>> BRANCH (eddec1 Import Cronet version 114.0.5715.0)

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
<<<<<<< HEAD   (8c5f24 cronet: update METADATA to version 110)
        return nativeCreateMockCertVerifier(certs, knownRoot,
                TestFilesInstaller.getInstalledPath(ContextUtils.getApplicationContext()));
=======
        return MockCertVerifierJni.get().createMockCertVerifier(
                certs, knownRoot, UrlUtils.getIsolatedTestRoot());
>>>>>>> BRANCH (eddec1 Import Cronet version 114.0.5715.0)
    }

    /**
     * Creates a new free-for-all net::MockCertVerifier and returns a pointer to it.
     *
     * @return a pointer to the newly created net::MockCertVerifier.
     */
    public static long createFreeForAllMockCertVerifier() {
        return MockCertVerifierJni.get().createFreeForAllMockCertVerifier();
    }

    @NativeMethods("cronet_tests")
    interface Natives {
        long createMockCertVerifier(String[] certs, boolean knownRoot, String testDataDir);
        long createFreeForAllMockCertVerifier();
    }
}
