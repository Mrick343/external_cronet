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


import com.android.ddmlib.testrunner.IRemoteAndroidTestRunner;
import com.android.tradefed.config.IConfiguration;
import com.android.tradefed.config.IConfigurationReceiver;
import com.android.tradefed.config.Option;
import com.android.tradefed.device.DeviceNotAvailableException;
import com.android.tradefed.device.ITestDevice;
import com.android.tradefed.invoker.TestInformation;
import com.android.tradefed.log.LogUtil;
import com.android.tradefed.metrics.proto.MetricMeasurement;
import com.android.tradefed.result.CollectingTestListener;
import com.android.tradefed.result.ITestInvocationListener;
import com.android.tradefed.result.TestDescription;
import com.android.tradefed.result.TestRunResult;
import com.android.tradefed.result.ddmlib.DefaultRemoteAndroidTestRunner;
import com.android.tradefed.testtype.IDeviceTest;
import com.android.tradefed.testtype.IRemoteTest;
import com.android.tradefed.testtype.ITestAnnotationFilterReceiver;
import com.android.tradefed.testtype.ITestCollector;
import com.android.tradefed.testtype.ITestFilterReceiver;
import com.android.tradefed.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ChromiumAndroidJUnitTestRunner implements IDeviceTest, IRemoteTest, ITestCollector,
        ITestFilterReceiver, ITestAnnotationFilterReceiver, IConfigurationReceiver {

    /** max number of attempts to collect list of tests in package */
    private static final int COLLECT_TESTS_ATTEMPTS = 3;
    /** default timeout for tests collection */
    static final long TEST_COLLECTION_TIMEOUT_MS = 2 * 60 * 1000;
    /** instrumentation test runner argument key used for including a class/test */
    private static final String INCLUDE_CLASS_INST_ARGS_KEY = "class";
    /** instrumentation test runner argument key used for excluding a class/test */
    private static final String EXCLUDE_CLASS_INST_ARGS_KEY = "notClass";
    /** instrumentation test runner argument key used for including a test regex */
    private static final String INCLUDE_REGEX_INST_ARGS_KEY = "tests_regex";
    /** instrumentation test runner argument key used for adding annotation filter */
    private static final String ANNOTATION_INST_ARGS_KEY = "annotation";
    /** instrumentation test runner argument key used for adding notAnnotation filter */
    private static final String NOT_ANNOTATION_INST_ARGS_KEY = "notAnnotation";
    /** instrumentation test runner argument key used for setting coverageFile path */
    private static final String COVERAGE_FILE_PATH_KEY = "coverageFile";
    /** instrumentation test runner argument key used for setting listeners */
    private static final String LISTENER_KEY = "listener";

    @Option(
            name = "collect-tests-only",
            description =
                    "Only invoke the instrumentation to collect list of applicable test cases. All"
                            + " test run callbacks will be triggered, but test execution will not"
                            + " be"
                            + " actually carried out.")
    private boolean mCollectTestsOnly = false;
    @Option(
            name = "include-filter",
            description = "The include filters of the test name to run.",
            requiredForRerun = true)
    private Set<String> mIncludeFilters = new LinkedHashSet<>();

    @Option(
            name = "exclude-filter",
            description = "The exclude filters of the test name to run.",
            requiredForRerun = true)
    private Set<String> mExcludeFilters = new LinkedHashSet<>();

    @Option(
            name = "include-annotation",
            description = "The annotation class name of the test name to run, can be repeated",
            requiredForRerun = true)
    private Set<String> mIncludeAnnotation = new HashSet<>();

    @Option(
            name = "exclude-annotation",
            description = "The notAnnotation class name of the test name to run, can be repeated",
            requiredForRerun = true)
    private Set<String> mExcludeAnnotation = new HashSet<>();
    @Option(
            name = "package",
            shortName = 'p',
            description = "The manifest package name of the Android test application to run.")
    private String mPackageName = null;

    @Option(
            name = "runner",
            description =
                    "The instrumentation test runner class name to use. Will try to determine "
                            + "automatically if it is not specified.")
    private String mRunnerName = null;

    @Option(
            name = "hidden-api-checks",
            description =
                    "If set to false, the '--no-hidden-api-checks' flag will be passed to the am "
                            + "instrument command. Only works for P or later.")
    private boolean mHiddenApiChecks = false;

    @Option(
            name = "isolated-storage",
            description =
                    "If set to false, the '--no-isolated-storage' flag will be passed to the am "
                            + "instrument command. Only works for Q or later.")
    private boolean mIsolatedStorage = false;

    @Option(
            name = "device-listeners",
            description =
                    "Specify device side instrumentation listeners to be added for the run. "
                            + "Can be repeated. Note that while the ordering here is followed for "
                            + "now, future versions of AndroidJUnitRunner might not preserve the "
                            + "listener ordering."
    )
    private List<String> mExtraDeviceListeners = new ArrayList<>();
    private ITestDevice mDevice;
    private IConfiguration mConfiguration;
    private CoverageUtils coverageUtils;

    /**
     * Executes each test in the test-suite passed individually, each test runs in its own
     * instrumentation invocation.
     *
     * @param testInfo The {@link TestInformation} object containing useful information to run
     *                 tests.
     * @param listener the {@link ITestInvocationListener} of test results
     */
    @Override
    public void run(TestInformation testInfo, ITestInvocationListener listener)
            throws DeviceNotAvailableException {
        coverageUtils = new CoverageUtils(mPackageName);
        Collection<TestDescription> tests = collectTestsToRun(mCollectTestsOnly ? listener : null);
        if (tests == null) {
            throw new IllegalStateException("Failed to collect tests! Check host logs.");
        }
        if (mCollectTestsOnly) {
            // We don't need to continue execution of the tests, we just need to collect tests only.
            return;
        }
        ITestInvocationListener accumulativeTestListener = new SingleTestListenerWrapper(listener,
                tests.size());
        long currentTime = System.currentTimeMillis();
        for (TestDescription test : tests) {
            instrumentIndividualTest(test, accumulativeTestListener);
        }
        if (mConfiguration.getCoverageOptions().isCoverageEnabled()) {
            coverageUtils.mergeAllCoverageReports(mDevice);
            listener.testRunEnded(System.currentTimeMillis() - currentTime,
                    coverageUtils.createMetricMap());
        } else {
            listener.testRunEnded(System.currentTimeMillis() - currentTime,
                    new HashMap<String, MetricMeasurement.Metric>());
        }
    }

    /**
     * Creates a new {@link IRemoteAndroidTestRunner} and executes the test. If
     * coverage is enabled then {@link #COVERAGE_FILE_PATH_KEY} gets added as an argument which
     * tells the test-runner where to dump the coverage file after the test execution has
     * finished.<br>
     *
     * See {@link CoverageUtils} for more details on why this is used.
     *
     * @param test     The TestDescription for the current test.
     * @param listener The listener that is listening to the test lifecycle.
     */
    private void instrumentIndividualTest(TestDescription test, ITestInvocationListener listener)
            throws DeviceNotAvailableException {
        IRemoteAndroidTestRunner runner = createRunner();
        configureRunner(runner, String.format("%s#%s", test.getClassName(), test.getTestName()));
        addDeviceListeners(runner);
        if (mConfiguration.getCoverageOptions().isCoverageEnabled()) {
            runner.addInstrumentationArg(COVERAGE_FILE_PATH_KEY,
                    coverageUtils.getCoverageFilePathForTest(test));
        }
        getDevice().runInstrumentationTests(runner, listener);
    }

    /**
     * Performs the actual work of collecting tests, making multiple attempts if necessary
     *
     * @param runner the {@link IRemoteAndroidTestRunner} that will be used for the
     *               instrumentation
     * @return the collection of tests, or <code>null</code> if tests could not be collected
     * @throws DeviceNotAvailableException if communication with the device was lost
     */
    private Collection<TestDescription> collectTestsAndRetry(
            final IRemoteAndroidTestRunner runner, ITestInvocationListener listener)
            throws DeviceNotAvailableException {
        for (int i = 0; i < COLLECT_TESTS_ATTEMPTS; i++) {
            CollectingTestListener collector = new CollectingTestListener();
            runner.setMaxTimeToOutputResponse(TEST_COLLECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            boolean instrResult;
            if (listener != null) {
                instrResult = mDevice.runInstrumentationTests(runner, collector, listener);
            } else {
                instrResult = mDevice.runInstrumentationTests(runner, collector);
            }
            TestRunResult runResults = collector.getCurrentRunResults();
            if (instrResult && runResults.isRunComplete()) {
                return runResults.getCompletedTests();
            } else if (!instrResult || runResults.isRunFailure()) {
                LogUtil.CLog.w(
                        "No results when collecting tests to run for %s on device %s. Retrying",
                        mPackageName, mDevice.getSerialNumber());
            }
        }
        LogUtil.CLog.e("Failed to collect tests!");
        return null;
    }

    private void addDeviceListeners(IRemoteAndroidTestRunner runner) {
        if (!mExtraDeviceListeners.isEmpty()) {
            runner.addInstrumentationArg(LISTENER_KEY, ArrayUtil.join(",", mExtraDeviceListeners));
        }
    }

    private Collection<TestDescription> collectTestsToRun(ITestInvocationListener listener)
            throws DeviceNotAvailableException {
        IRemoteAndroidTestRunner runner = createRunner();
        configureRunner(runner, mIncludeFilters);
        LogUtil.CLog.i("Collecting test info for %s on device %s", mPackageName,
                mDevice.getSerialNumber());
        runner.setTestCollection(true);
        return collectTestsAndRetry(runner, listener);
    }

    private IRemoteAndroidTestRunner createRunner() throws DeviceNotAvailableException {
        DefaultRemoteAndroidTestRunner runner = new DefaultRemoteAndroidTestRunner(mPackageName,
                mRunnerName, mDevice.getIDevice());
        String runOptions = "";
        if (!mHiddenApiChecks && getDevice().getApiLevel() >= 28) {
            runOptions += "--no-hidden-api-checks ";
        }
        // isolated-storage flag only exists in Q and after.
        if (!mIsolatedStorage && getDevice().checkApiLevelAgainstNextRelease(29)) {
            runOptions += "--no-isolated-storage ";
        }
        runner.setRunOptions(runOptions);
        return runner;
    }

    private void configureRunner(IRemoteAndroidTestRunner runner, String includeFilter) {
        configureRunner(runner, Set.of(includeFilter));
    }

    private void configureRunner(IRemoteAndroidTestRunner runner, Set<String> includeFilters) {
        if (!includeFilters.isEmpty()) {
            runner.addInstrumentationArg(INCLUDE_CLASS_INST_ARGS_KEY,
                    ArrayUtil.join(",", includeFilters));
        }
        if (!mExcludeFilters.isEmpty()) {
            runner.addInstrumentationArg(EXCLUDE_CLASS_INST_ARGS_KEY,
                    ArrayUtil.join(",", mExcludeFilters));
        }
        if (!mIncludeAnnotation.isEmpty()) {
            runner.addInstrumentationArg(ANNOTATION_INST_ARGS_KEY,
                    ArrayUtil.join(",", mIncludeAnnotation));
        }
        if (!mExcludeAnnotation.isEmpty()) {
            runner.addInstrumentationArg(NOT_ANNOTATION_INST_ARGS_KEY,
                    ArrayUtil.join(",", mExcludeAnnotation));
        }
    }

    @Override
    public void setDevice(ITestDevice device) {
        mDevice = device;
    }

    @Override
    public ITestDevice getDevice() {
        return mDevice;
    }

    @Override
    public void setCollectTestsOnly(boolean shouldCollectTest) {
        mCollectTestsOnly = shouldCollectTest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addIncludeFilter(String filter) {
        mIncludeFilters.add(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAllIncludeFilters(Set<String> filters) {
        mIncludeFilters.addAll(filters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addExcludeFilter(String filter) {
        mExcludeFilters.add(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAllExcludeFilters(Set<String> filters) {
        mExcludeFilters.addAll(filters);
    }

    /** {@inheritDoc} */
    @Override
    public void clearIncludeFilters() {
        mIncludeFilters.clear();
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> getIncludeFilters() {
        return mIncludeFilters;
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> getExcludeFilters() {
        return mExcludeFilters;
    }

    /** {@inheritDoc} */
    @Override
    public void clearExcludeFilters() {
        mExcludeFilters.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addIncludeAnnotation(String annotation) {
        mIncludeAnnotation.add(annotation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAllIncludeAnnotation(Set<String> annotations) {
        mIncludeAnnotation.addAll(annotations);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addExcludeAnnotation(String excludeAnnotation) {
        mExcludeAnnotation.add(excludeAnnotation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAllExcludeAnnotation(Set<String> excludeAnnotations) {
        mExcludeAnnotation.addAll(excludeAnnotations);
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> getIncludeAnnotations() {
        return mIncludeAnnotation;
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> getExcludeAnnotations() {
        return mExcludeAnnotation;
    }

    /** {@inheritDoc} */
    @Override
    public void clearIncludeAnnotations() {
        mIncludeAnnotation.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void clearExcludeAnnotations() {
        mExcludeAnnotation.clear();
    }

    @Override
    public void setConfiguration(IConfiguration configuration) {
        mConfiguration = configuration;
    }
}
