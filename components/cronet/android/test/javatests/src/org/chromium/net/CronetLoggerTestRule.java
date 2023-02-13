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

import androidx.annotation.NonNull;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import org.chromium.net.impl.CronetLogger;
import org.chromium.net.impl.CronetLoggerFactory.SwapLoggerForTesting;

/**
 * Custom TestRule that instantiates a new fake CronetLogger for each test.
 * @param <T> The actual type of the class extending CronetLogger.
 */
public class CronetLoggerTestRule<T extends CronetLogger> implements TestRule {
    private static final String TAG = CronetLoggerTestRule.class.getSimpleName();

    private Class<T> mTestLoggerClazz;

    // Expose the fake logger to the test.
    public T mTestLogger;

    public CronetLoggerTestRule(@NonNull Class<T> testLoggerClazz) {
        if (testLoggerClazz == null) {
            throw new NullPointerException("TestLoggerClazz is required.");
        }

        mTestLoggerClazz = testLoggerClazz;
    }

    @Override
    public Statement apply(final Statement base, final Description desc) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try (SwapLoggerForTesting swapper = buildSwapper()) {
                    base.evaluate();
                } finally {
                    mTestLogger = null;
                }
            }
        };
    }

    private SwapLoggerForTesting buildSwapper() {
        assert mTestLoggerClazz != null;

        try {
            mTestLogger = mTestLoggerClazz.getConstructor().newInstance();
            return new SwapLoggerForTesting(mTestLogger);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException(
                    "CronetTestBase#runTest failed while swapping TestLogger.", e);
        }
    }
}
