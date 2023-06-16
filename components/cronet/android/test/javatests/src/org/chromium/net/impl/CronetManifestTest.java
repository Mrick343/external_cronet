// Copyright 2022 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.chromium.net.CronetTestRule.getContext;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.chromium.base.test.util.PackageManagerWrapper;
import org.chromium.net.CronetTestRule;
import org.chromium.net.CronetTestRule.OnlyRunNativeCronet;
import org.chromium.net.impl.CronetLogger.CronetSource;

/**
 * Tests {@link CronetManifest}
 */
@RunWith(AndroidJUnit4.class)
public class CronetManifestTest {
    @Rule
    public final CronetTestRule mTestRule = new CronetTestRule();

    private Context mMockContext;
    private Bundle mMetadata;
    private ApplicationInfo mAppInfo;

    @Before
    public void setUp() throws Exception {
        mAppInfo = new ApplicationInfo();
        mMockContext = new MockContext(getContext());
        mMetadata = new Bundle();
    }

    @Test
    @SmallTest
    @OnlyRunNativeCronet
    public void testTelemetryOptIn_whenNoMetadata() throws Exception {
        assertTrue(CronetManifest.isAppOptedInForTelemetry(
                mMockContext, CronetSource.CRONET_SOURCE_PLATFORM));
    }

    @Test
    @SmallTest
    @OnlyRunNativeCronet
    public void testTelemetryOptIn_whenMetadataIsTrue() throws Exception {
        mMetadata.putBoolean(CronetManifest.TELEMETRY_OPT_OUT_META_DATA_STR, true);
        mAppInfo.metaData = mMetadata;

        assertFalse(CronetManifest.isAppOptedInForTelemetry(
                mMockContext, CronetSource.CRONET_SOURCE_PLATFORM));
    }

    @Test
    @SmallTest
    @OnlyRunNativeCronet
    public void testTelemetryOptIn_whenMetadataIsFalse() throws Exception {
        mMetadata.putBoolean(CronetManifest.TELEMETRY_OPT_OUT_META_DATA_STR, false);
        mAppInfo.metaData = mMetadata;

        assertTrue(CronetManifest.isAppOptedInForTelemetry(
                mMockContext, CronetSource.CRONET_SOURCE_PLATFORM));
    }

    private class MockContext extends ContextWrapper {
        public MockContext(Context base) {
            super(base);
        }

        @Override
        public PackageManager getPackageManager() {
            return new PackageManagerWrapper(super.getPackageManager()) {
                @Override
                public ApplicationInfo getApplicationInfo(String packageName, int flags)
                        throws PackageManager.NameNotFoundException {
                    return mAppInfo;
                }
            };
        }
    }
}
