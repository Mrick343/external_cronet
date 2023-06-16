// Copyright 2022 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net.impl;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.VisibleForTesting;

import org.chromium.net.impl.CronetLogger.CronetSource;

/**
 * Utility class for working with the AndroidManifest flags.
 */
@VisibleForTesting
public final class CronetManifest {
    private CronetManifest() {}
    // Individual apps can use this meta-data tag in their manifest to opt out of telemetry.
    // TODO(sporeba@): Add this to the android documentation
    static final String TELEMETRY_OPT_OUT_META_DATA_STR = "android.net.http.DisableHttpEngineTelemetry";

    @VisibleForTesting
    public static boolean isAppOptedInForTelemetry(Context ctx, CronetSource source) {
        try {
            // Check if app is opted in
            ApplicationInfo info = ctx.getPackageManager().getApplicationInfo(
                    ctx.getPackageName(), PackageManager.GET_META_DATA);

            // TODO(b/226553652): Enable logging if loaded from CRONET_PLAY_SERVICES, after testing
            //  with select users
            if (info.metaData == null) {
                return true;
            }

            // getBoolean returns false if the key is not found, which is what we want.
            boolean appHasOptedOut = info.metaData.getBoolean(TELEMETRY_OPT_OUT_META_DATA_STR);
            return !appHasOptedOut;
        } catch (PackageManager.NameNotFoundException e) {
            // This should never happen.
            // Platform Cronet assumes the app has not opted out.
            return true;
        }
    }
}
