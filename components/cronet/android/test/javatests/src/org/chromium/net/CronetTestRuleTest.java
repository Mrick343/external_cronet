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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import org.chromium.net.CronetTestRule.CronetTestFramework;
import org.chromium.net.CronetTestRule.OnlyRunNativeCronet;
import org.chromium.net.CronetTestRule.RequiresMinApi;
import org.chromium.net.impl.CronetUrlRequestContext;

/**
 * Tests features of CronetTestRule.
 */
@RunWith(AndroidJUnit4.class)
public class CronetTestRuleTest {
    @Rule
    public final CronetTestRule mTestRule = new CronetTestRule();
    @Rule
    public final TestName mTestName = new TestName();

    private CronetTestFramework mTestFramework;
    /**
     * For any test whose name contains "MustRun", it's enforced that the test must run and set
     * {@code mTestWasRun} to {@code true}.
     */
    private boolean mTestWasRun;

    @Before
    public void setUp() throws Exception {
        mTestWasRun = false;
        mTestFramework = mTestRule.startCronetTestFramework();
    }

    @After
    public void tearDown() throws Exception {
        if (mTestName.getMethodName().contains("MustRun") && !mTestWasRun) {
            fail(mTestName.getMethodName() + " should have run but didn't." + mTestRule.getMaximumAvailableApiLevel());
        }
    }

    @Test
    @SmallTest
    @RequiresMinApi(999999999)
    public void testRequiresMinApiDisable() {
        fail("RequiresMinApi failed to disable.");
    }

    @Test
    @SmallTest
    @RequiresMinApi(-999999999)
    public void testRequiresMinApiMustRun() {
        mTestWasRun = true;
    }

    @Test
    @SmallTest
    @OnlyRunNativeCronet
    public void testRunOnlyNativeMustRun() {
        assertFalse(mTestRule.testingJavaImpl());
        assertFalse(mTestWasRun);
        mTestWasRun = true;
        assertEquals(mTestFramework.mCronetEngine.getClass(), CronetUrlRequestContext.class);
    }

}
