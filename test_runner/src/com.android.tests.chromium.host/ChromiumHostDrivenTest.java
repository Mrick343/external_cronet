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

import com.android.tradefed.device.CollectingOutputReceiver;
import com.android.tradefed.device.DeviceNotAvailableException;
import com.android.tradefed.log.LogUtil;
import com.android.tradefed.testtype.DeviceJUnit4ClassRunner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import com.android.tradefed.testtype.junit4.BaseHostJUnit4Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RunWith(DeviceJUnit4ClassRunner.class)
public class ChromiumHostDrivenTest extends BaseHostJUnit4Test {
    static final CollectingOutputReceiver mOutputReceiver = new CollectingOutputReceiver();
    static final String CHROMIUM_PACKAGE = "org.chromium.native_test";
    static final String NATIVE_TEST_ACTIVITY_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTestInstrumentationTestRunner.NativeTestActivity");
    static final String RUN_IN_SUBTHREAD_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTest.RunInSubThread");
    static final String NATIVE_UNIT_TEST_ACTIVITY_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeUnitTestActivity");
    static final String SHARD_NANO_TIMEOUT_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTestInstrumentationTestRunner.ShardNanoTimeout");
    static final String COMMAND_LINE_FLAGS_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTest.CommandLineFlags");
    static final String STDOUT_FILE_KEY = String.format("%s.%s", CHROMIUM_PACKAGE,
            "NativeTestInstrumentationTestRunner.StdoutFile");
    static final String TEST_RUNNER = String.format("%s/%s", CHROMIUM_PACKAGE,
            "org.chromium.build.gtest_apk.NativeTestInstrumentationTestRunner");
    static final String FAILURE_MESSAGE =
            "Failed to fetch gtest output file. Expected file destination: %s\n"
                    + "To rerun the gtests, run the following command:\n"
                    + "adb shell %s";
    static final Duration TESTS_TIMEOUT = Duration.ofSeconds(60);
    static final CollectingOutputReceiver OUTPUT_COLLECTOR = new CollectingOutputReceiver();

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        try {
            // This is needed to ensure that files are readable from the APK
            getDevice().executeShellCommand("setenforce 0");
        } catch (DeviceNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        try {
            getDevice().executeShellCommand("setenforce 1");
        } catch (DeviceNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private String createTempFile() throws DeviceNotAvailableException {
        String filePath = String.format("/data/local/tmp/temp_file-%s", UUID.randomUUID());
        getDevice().executeShellCommand(String.format("touch %s", filePath));
        return filePath;
    }

    private void appendTupleToCommand(StringBuilder cmd, String key, String value) {
        cmd.append("-e ");
        if (value != null) {
            cmd.append(key).append(" ").append(value).append(" ");
        } else {
            cmd.append(key).append(" ");
        }
    }

    private String createRunAllTestsCommand(String tempFilePathOnDevice) {
        StringBuilder cmd = new StringBuilder();
        cmd.append("am instrument -w ");
        appendTupleToCommand(cmd, NATIVE_TEST_ACTIVITY_KEY, NATIVE_UNIT_TEST_ACTIVITY_KEY);
        appendTupleToCommand(cmd, RUN_IN_SUBTHREAD_KEY, "1");
        String cmdLineFlags = String.format("'--gtest_output=json:%s'", tempFilePathOnDevice);
        appendTupleToCommand(cmd, COMMAND_LINE_FLAGS_KEY, cmdLineFlags);
        appendTupleToCommand(cmd, SHARD_NANO_TIMEOUT_KEY, "30000000000");
        cmd.append(TEST_RUNNER);
        return cmd.toString();
    }

    private File getOutputFile(String tempFileOnDevice)
            throws DeviceNotAvailableException {
        if (getDevice().doesFileExist(tempFileOnDevice)) {
            return getDevice().pullFile(tempFileOnDevice);
        }
        return null;
    }

    @Test
    public void testRunChromiumTests() throws Exception {
        String tempFileOnDevice = createTempFile();
        String cmd = createRunAllTestsCommand(tempFileOnDevice);
        LogUtil.CLog.i(String.format("Command used to run gtests\n%s", cmd));
        getDevice().executeShellCommand(cmd, OUTPUT_COLLECTOR, TESTS_TIMEOUT.toSeconds(),
                TimeUnit.SECONDS, 3);
        File outputFile = getOutputFile(tempFileOnDevice);
        LogUtil.CLog.i("Instrumentation Outputs\n%s", OUTPUT_COLLECTOR.getOutput());
        if (outputFile == null) {
            Assert.fail(String.format(FAILURE_MESSAGE, tempFileOnDevice, cmd));
        }
        GTestsMetaData gTestsMetaData = new GTestsMetaData(outputFile);
        // Print pretty log
        gTestsMetaData.logTestsOutput();
        // TODO(aymanm): Remove this once the pretty log has all of the information needed
        List<String> lines = Files.readAllLines(outputFile.toPath());
        for (String line : lines) {
            LogUtil.CLog.i(line);
        }
        Assert.assertFalse(gTestsMetaData.hasAnyFailures());
        if (gTestsMetaData.getTotalTests() == 0) {
            LogUtil.CLog.w("No Test has been executed.");
        }
    }
}