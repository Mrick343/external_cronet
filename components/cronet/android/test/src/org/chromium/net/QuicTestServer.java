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

import android.content.Context;
import android.os.ConditionVariable;

import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.test.util.UrlUtils;

/**
 * Wrapper class to start a Quic test server.
 */
@JNINamespace("cronet")
public final class QuicTestServer {
    private static final ConditionVariable sBlock = new ConditionVariable();
    private static final String TAG = QuicTestServer.class.getSimpleName();

    private static final String CERT_USED = "quic-chain.pem";
    private static final String KEY_USED = "quic-leaf-cert.key";
    private static final String[] CERTS_USED = {CERT_USED};

    private static boolean sServerRunning;

    /*
     * Starts the server.
     */
    public static void startQuicTestServer(Context context) throws Exception {
        if (sServerRunning) {
            throw new IllegalStateException("Quic server is already running");
        }
        TestFilesInstaller.installIfNeeded(context);
        String path = TestFilesInstaller.getInstalledPath(context);
        nativeStartQuicTestServer(path, path);
        sBlock.block();
        sBlock.close();
        sServerRunning = true;
    }

    /**
     * Shuts down the server. No-op if the server is already shut down.
     */
    public static void shutdownQuicTestServer() {
        if (!sServerRunning) {
            return;
        }
        nativeShutdownQuicTestServer();
        sServerRunning = false;
    }

    public static String getServerURL() {
        return "https://" + getServerHost() + ":" + getServerPort();
    }

    public static String getServerHost() {
        return CronetTestUtil.QUIC_FAKE_HOST;
    }

    public static int getServerPort() {
        return nativeGetServerPort();
    }

    public static final String getServerCert() {
        return CERT_USED;
    }

    public static final String getServerCertKey() {
        return KEY_USED;
    }

    public static long createMockCertVerifier() {
//        TestFilesInstaller.installIfNeeded(ContextUtils.getApplicationContext());
        return MockCertVerifier.createMockCertVerifier(CERTS_USED, true);
    }

    @CalledByNative
    private static void onServerStarted() {
        Log.i(TAG, "Quic server started.");
        sBlock.open();
    }

    private static native void nativeStartQuicTestServer(String filePath, String testDataDir);
    private static native void nativeShutdownQuicTestServer();
    private static native int nativeGetServerPort();
}
