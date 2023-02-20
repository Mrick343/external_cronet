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

package com.android.tests.chromium.host;

import com.android.tradefed.config.Option;
import com.android.tradefed.device.CollectingOutputReceiver;
import com.android.tradefed.device.DeviceNotAvailableException;
import com.android.tradefed.log.LogUtil;
import com.android.tradefed.testtype.DeviceJUnit4ClassRunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.android.tradefed.testtype.junit4.BaseHostJUnit4Test;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(DeviceJUnit4ClassRunner.class)
public class ChromiumHostDrivenTest extends BaseHostJUnit4Test {
    static final String CHROMIUM_PACKAGE = "org.chromium.native_test";
    static final String NATIVE_TEST_ACTIVITY_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTestInstrumentationTestRunner.NativeTestActivity");
    static final String RUN_IN_SUBTHREAD_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTest.RunInSubThread");
    static final String NATIVE_UNIT_TEST_ACTIVITY_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeUnitTestActivity");
    static final String COMMAND_LINE_FLAGS_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTest.CommandLineFlags");
    static final String DUMP_COVERAGE_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTestInstrumentationTestRunner.DumpCoverage");
    static final String SHARD_NANO_TIMEOUT_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTestInstrumentationTestRunner.ShardNanoTimeout");
    static final String STDOUT_FILE_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTestInstrumentationTestRunner.StdoutFile");
    static final String TEST_RUNNER = String.format("%s/%s", CHROMIUM_PACKAGE,
            "org.chromium.build.gtest_apk.NativeTestInstrumentationTestRunner");
    static final String FAILURE_MESSAGE =
            "Failed to fetch gtest output file. Expected file destination: %s\n"
                    + "To rerun the gtests, run the following command:\n"
                    + "adb shell %s";
    static Duration testsTimeout = Duration.ofMinutes(30);
    static final CollectingOutputReceiver OUTPUT_COLLECTOR = new CollectingOutputReceiver();
    // This contains the gtest runner result output.
    static final String GTEST_RESULT_OUTPUT_PATH =
            "/data/local/tmp/cronet_test_results_output.json";
    // This contains the gtest logs that is printed to stdout.
    static final String GTEST_OUTPUT_PATH = "/data/local/tmp/cronet_gtest_output.txt";
    private static final String CLEAR_CLANG_COVERAGE_FILES =
            "find /data/misc/trace -name '*.profraw' -delete";

    @Option(
            name = "dump-native-coverage",
            description = "Force APK under test to dump native test coverage upon exit"
    )
    private boolean mCoverage = false;

    private String createRunAllTestsCommand() {
        InstrumentationCommandBuilder builder = new InstrumentationCommandBuilder(TEST_RUNNER)
                .addArgument(NATIVE_TEST_ACTIVITY_KEY, NATIVE_UNIT_TEST_ACTIVITY_KEY)
                .addArgument(RUN_IN_SUBTHREAD_KEY, "1")
                .addArgument(STDOUT_FILE_KEY, GTEST_OUTPUT_PATH)
                .addArgument(COMMAND_LINE_FLAGS_KEY, String.format("'--gtest_output=json:%s'",
                        ChromiumHostDrivenTest.GTEST_RESULT_OUTPUT_PATH));
        if (mCoverage) {
            builder.addArgument(DUMP_COVERAGE_KEY, "true");
        }
        return builder.build();
    }

    @Before
    public void setup() throws DeviceNotAvailableException {
        if (mCoverage) {
            getDevice().executeShellCommand(CLEAR_CLANG_COVERAGE_FILES);
            testsTimeout = Duration.ofMinutes(10);
        }
    }

    @Test
    public void testRunChromiumTests() throws Exception {
        String cmd = createRunAllTestsCommand();
        LogUtil.CLog.i(String.format("Command used to run gtests\n%s", cmd));
        if (mCoverage) {
            LogUtil.CLog.i("dump-native-coverage enabled!");
        }
        getDevice().executeShellCommand(cmd, OUTPUT_COLLECTOR, testsTimeout.toMinutes(),
                TimeUnit.MINUTES, 3);
        File gtestTestResultsJson = getDevice().pullFile(GTEST_RESULT_OUTPUT_PATH);
        File testsOutput = getDevice().pullFile(GTEST_OUTPUT_PATH);
        if (testsOutput == null) {
            LogUtil.CLog.w("Couldn't find tests stdout output file.");
        } else {
            LogUtil.CLog.i("========= START OF TESTS STDOUT =========");
            for (String line : Files.readAllLines(testsOutput.toPath())) {
                LogUtil.CLog.i(line);
            }
            LogUtil.CLog.i("========= END OF TESTS STDOUT =========");
        }
        LogUtil.CLog.i("========= START OF INSTRUMENTATION OUTPUT =========");
        LogUtil.CLog.i("%s", OUTPUT_COLLECTOR.getOutput());
        LogUtil.CLog.i("========= END OF INSTRUMENTATION OUTPUT =========");
        if (gtestTestResultsJson == null) {
            Assert.fail(String.format(FAILURE_MESSAGE, GTEST_RESULT_OUTPUT_PATH, cmd));
        }
        GTestsMetaData gTestsMetaData = new GTestsMetaData(gtestTestResultsJson);
        // Print pretty log
        gTestsMetaData.logTestsOutput();
        // TODO(aymanm): Remove this once the pretty log has all of the information needed
        List<String> lines = Files.readAllLines(gtestTestResultsJson.toPath());
        for (String line : lines) {
            LogUtil.CLog.i(line);
        }
        Assert.assertFalse(gTestsMetaData.hasAnyFailures());
        if (!gTestsMetaData.isOutputParsedCorrectly()) {
            // Assert.fail("Failed to parse gtests result. "
               //     + "Please check the logcat for more information.");
        } else if (gTestsMetaData.getTotalTests() == 0) {
            Assert.fail("No Test has been executed.");
        }
    }
}