// Copyright 2022 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net.impl;

import static com.google.common.truth.Truth.assertThat;

import android.os.Bundle;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.chromium.net.CronetTestRuleNew;
import org.chromium.net.CronetTestRuleNew.CronetTestFramework;
import org.chromium.net.CronetTestRuleNew.OnlyRunNativeCronet;
import org.chromium.net.impl.CronetLogger.CronetSource;

/**
 * Tests {@link CronetManifest}
 */
@RunWith(AndroidJUnit4.class)
@OnlyRunNativeCronet
public class CronetManifestTest {
    @Rule
    public final CronetTestRuleNew mTestRule = CronetTestRuleNew.withManualEngineStartup();

    public CronetTestFramework mCronetTestFramework;
    @Before
    public void setUp() {
        mCronetTestFramework = mTestRule.getTestFramework();
    }

    private void setTelemetryOptIn(boolean value) {
        Bundle metaData = new Bundle();
        metaData.putBoolean(CronetManifest.ENABLE_TELEMETRY_META_DATA_KEY, value);
        mCronetTestFramework.interceptContext(new CronetManifestInterceptor(metaData));
    }

    @Test
    @SmallTest
    public void testTelemetryOptIn_whenNoMetadata() throws Exception {
        assertThat(CronetManifest.isAppOptedInForTelemetry(mCronetTestFramework.getContext(),
                           CronetSource.CRONET_SOURCE_STATICALLY_LINKED))
                .isFalse();
        assertThat(CronetManifest.isAppOptedInForTelemetry(mCronetTestFramework.getContext(),
                           CronetSource.CRONET_SOURCE_PLAY_SERVICES))
                .isFalse();
        assertThat(CronetManifest.isAppOptedInForTelemetry(
                           mCronetTestFramework.getContext(), CronetSource.CRONET_SOURCE_FALLBACK))
                .isFalse();
    }

    @Test
    @SmallTest
    public void testTelemetryOptIn_whenMetadataIsTrue() throws Exception {
        setTelemetryOptIn(true);
        assertThat(CronetManifest.isAppOptedInForTelemetry(mCronetTestFramework.getContext(),
                           CronetSource.CRONET_SOURCE_STATICALLY_LINKED))
                .isTrue();
        assertThat(CronetManifest.isAppOptedInForTelemetry(mCronetTestFramework.getContext(),
                           CronetSource.CRONET_SOURCE_PLAY_SERVICES))
                .isTrue();
        assertThat(CronetManifest.isAppOptedInForTelemetry(
                           mCronetTestFramework.getContext(), CronetSource.CRONET_SOURCE_FALLBACK))
                .isTrue();
    }

    @Test
    @SmallTest
    public void testTelemetryOptIn_whenMetadataIsFalse() throws Exception {
        setTelemetryOptIn(false);
        assertThat(CronetManifest.isAppOptedInForTelemetry(mCronetTestFramework.getContext(),
                           CronetSource.CRONET_SOURCE_STATICALLY_LINKED))
                .isFalse();
        assertThat(CronetManifest.isAppOptedInForTelemetry(mCronetTestFramework.getContext(),
                           CronetSource.CRONET_SOURCE_PLAY_SERVICES))
                .isFalse();
        assertThat(CronetManifest.isAppOptedInForTelemetry(
                           mCronetTestFramework.getContext(), CronetSource.CRONET_SOURCE_FALLBACK))
                .isFalse();
    }
}
