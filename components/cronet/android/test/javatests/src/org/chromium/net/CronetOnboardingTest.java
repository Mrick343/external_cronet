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

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * A test file which is used to teach people how to build, test and submit changes to Cronet. Do not
 * move, change or delete unless you modify the onboarding instructions as well.
 */
@RunWith(AndroidJUnit4.class)
public class CronetOnboardingTest {
    // TODO(noogler): STEP 1 - add your name here
    private static final List<String> CRONET_CONTRIBUTORS =
            Arrays.asList("colibie", "danstahr", "sporeba", "stefanoduo");

    // TODO(noogler): STEP 2 - run the test suite and see it fail
    @Test
    @SmallTest
    public void testNumberOfCronetContributors() throws Exception {
        // TODO(noogler): STEP 3 - fix the test, rerun it and see it pass
        assertEquals(4, CRONET_CONTRIBUTORS.size());
    }
}
