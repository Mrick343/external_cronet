// Copyright 2015 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net;

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.NativeMethods;
=======
import org.jni_zero.JNINamespace;
import org.jni_zero.NativeMethods;

>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
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
        return MockCertVerifierJni.get().createMockCertVerifier(certs, knownRoot,
                TestFilesInstaller.getInstalledPath(ContextUtils.getApplicationContext()));
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
    public interface Natives {
        long createMockCertVerifier(String[] certs, boolean knownRoot, String testDataDir);
        long createFreeForAllMockCertVerifier();
    }
}
