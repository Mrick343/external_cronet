/*
 * Copyright (C) 2024 The Android Open Source Project
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

package com.android.tests.chromium.device;

import com.android.ddmlib.testrunner.InstrumentationResultParser;
import com.android.tradefed.metrics.proto.MetricMeasurement;
import com.android.tradefed.result.FailureDescription;
import com.android.tradefed.result.ITestInvocationListener;
import com.android.tradefed.result.TestDescription;

import org.junit.runner.Description;

import java.util.HashMap;
import java.util.Map;

/**
 * SingleTestListenerWrapper exists in order to integrate correctly
 * with the rest of the infrastructure, The problem with running
 * each test individually is that `testRunEnded` fires after an
 * instrumentation invocation has finished, this results in the infra
 * thinking that we ran N different modules instead of just 1 module
 * with N tests. <br> <br>
 *
 * Another problem is `testRunStarted` is called everytime an instrumentation
 * invocation has fired, this also confuses the infra more. In order to solve
 * this problem, `testRunStarted` will only run once and never again to ensure
 * that we only report the start of the test execution once and for `testRunEnded`
 * we call it manually from {@link ChromiumAndroidJUnitTestRunner} <br> <br>
 *
 * {@link SingleTestListenerWrapper#testRunStarted} gets called from androidx-test-runner as seen
 * {@link
 * com.android.ddmlib.testrunner.InstrumentationResultParser#reportTestRunStarted(InstrumentationResultParser.TestResult)}
 * which will always report 1 for num of tests because each invocation has only a single test. This
 * is the reason why we have to override the result with
 * {@link SingleTestListenerWrapper#mTestCount}. <br><br>
 *
 * The rest of the calls are delegated to the wrapped listener as-is.
 */
public class SingleTestListenerWrapper implements
        ITestInvocationListener {
    private final int mTestCount;
    private final ITestInvocationListener mDelegate;
    private boolean isTestRunStarted = false;

    public SingleTestListenerWrapper(ITestInvocationListener delegate, int testCount) {
        mDelegate = delegate;
        mTestCount = testCount;
    }

    @Override
    public void testRunStarted(String runName, int testCount) {
        if (!isTestRunStarted) {
            isTestRunStarted = true;
            mDelegate.testRunStarted(runName, mTestCount);
        }
    }

    @Override
    public void testRunStarted(String runName, int testCount, int attemptNumber) {
        if (!isTestRunStarted) {
            isTestRunStarted = true;
            mDelegate.testRunStarted(runName, mTestCount, attemptNumber);
        }
    }

    @Override
    public void testRunStarted(String runName, int testCount, int attemptNumber, long startTime) {
        if (!isTestRunStarted) {
            isTestRunStarted = true;
            mDelegate.testRunStarted(runName, mTestCount, attemptNumber, startTime);
        }
    }

    @Override
    public void testStarted(TestDescription test) {
        mDelegate.testStarted(test);
    }

    @Override
    public void testStarted(TestDescription test, long startTime) {
        mDelegate.testStarted(test, startTime);
    }

    @Override
    public void testEnded(TestDescription test, Map<String, String> testMetrics) {
        mDelegate.testEnded(test, testMetrics);
    }

    @Override
    public void testEnded(TestDescription test,
            HashMap<String, MetricMeasurement.Metric> testMetrics) {
        mDelegate.testEnded(test, testMetrics);
    }

    @Override
    public void testEnded(TestDescription test, long endTime, Map<String, String> testMetrics) {
        mDelegate.testEnded(test, endTime, testMetrics);
    }


    @Override
    public void testEnded(TestDescription test, long endTime,
            HashMap<String, MetricMeasurement.Metric> testMetrics) {
        mDelegate.testEnded(test, endTime, testMetrics);
    }

    @Override
    public void testRunFailed(String errorMessage) {
        mDelegate.testRunFailed(errorMessage);
    }

    @Override
    public void testRunFailed(FailureDescription failure) {
        mDelegate.testRunFailed(failure);
    }

    @Override
    public void testAssumptionFailure(TestDescription test, String trace) {
        mDelegate.testAssumptionFailure(test, trace);
    }

    @Override
    public void testFailed(TestDescription test, String trace) {
        mDelegate.testFailed(test, trace);
    }

    @Override
    public void testFailed(TestDescription test, FailureDescription failure) {
        mDelegate.testFailed(test, failure);
    }

    @Override
    public void testIgnored(TestDescription test) {
        mDelegate.testIgnored(test);
    }
}
