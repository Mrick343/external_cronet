// Copyright 2015 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net.urlconnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

<<<<<<< HEAD   (a4cf74 Merge remote-tracking branch 'aosp/master' into upstream-sta)
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.chromium.net.CronetTestRule;

/**
 * Test for CronetURLStreamHandlerFactory.
 */
@RunWith(AndroidJUnit4.class)
@SuppressWarnings("deprecation")
public class CronetURLStreamHandlerFactoryTest {
    @Rule
    public final CronetTestRule mTestRule = new CronetTestRule();

    @Test
    @SmallTest
=======
import android.support.test.runner.AndroidJUnit4;

import androidx.test.filters.SmallTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.chromium.base.test.util.Feature;
import org.chromium.net.CronetTestRule;

/**
 * Test for CronetURLStreamHandlerFactory.
 */
@RunWith(AndroidJUnit4.class)
@SuppressWarnings("deprecation")
public class CronetURLStreamHandlerFactoryTest {
    @Rule
    public final CronetTestRule mTestRule = new CronetTestRule();

    @Test
    @SmallTest
    @Feature({"Cronet"})
>>>>>>> BRANCH (14c906 Import Cronet version 108.0.5359.128)
    public void testRequireConfig() throws Exception {
        mTestRule.startCronetTestFramework();
        try {
            new CronetURLStreamHandlerFactory(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals("CronetEngine is null.", e.getMessage());
        }
    }
}
