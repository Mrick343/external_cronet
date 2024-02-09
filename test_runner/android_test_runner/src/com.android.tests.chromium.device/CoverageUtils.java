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

import com.android.ddmlib.IDevice;
import com.android.tradefed.device.DeviceNotAvailableException;
import com.android.tradefed.device.ITestDevice;
import com.android.tradefed.device.metric.DeviceMetricData;
import com.android.tradefed.log.LogUtil;
import com.android.tradefed.metrics.proto.MetricMeasurement;
import com.android.tradefed.result.TestDescription;
import com.android.tradefed.util.AdbRootElevator;
import com.android.tradefed.util.CommandResult;
import com.android.tradefed.util.CommandStatus;
import com.android.tradefed.util.FileUtil;

import org.jacoco.core.tools.ExecFileLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link CoverageUtils} exists in order to integrate correctly
 * with the rest of the infrastructure, at the time of writing, the infrastructure
 * assumes that a test-suite will generate a single file that contains the coverage
 * data needed. However, this is not true for our use-case where we will generate N
 * coverage data file, 1 file for each test-case. In order keep everything consistent
 * with the infra, each instrumentation invocation will set
 * {@link ChromiumAndroidJUnitTestRunner#COVERAGE_FILE_PATH_KEY} to a different string where the
 * instrumentation will dump the coverage to that path, after all the tests has finished execution,
 * merge all of the coverage files into one and report back to the infra the location of the merged
 * coverage file. <br><br>
 *
 * Reporting back to infra is done through calling
 * {@link com.android.tradefed.result.ITestInvocationListener#testRunEnded(long, HashMap)} and
 * adding
 * the path to the hashmap to be accessed by
 * {@link
 * com.android.tradefed.device.metric.JavaCodeCoverageCollector#onTestRunEnd(DeviceMetricData,
 * Map)}. <br><br>
 *
 * This should make the coverage merging transparent to the infrastructure.
 */
public class CoverageUtils {
    /** Name of the merged coverage file that will live on the device */
    private static final String MERGED_COVERAGE_FILE = "merged_coverage.ec";

    private final String mPackageName;

    public CoverageUtils(String packageName) {
        mPackageName = packageName;
    }

    private void deleteIndividualCoverageFiles(ITestDevice device)
            throws DeviceNotAvailableException {
        device.executeShellV2Command("find %s -name '*.ec' -delete");
    }

    public String getCoverageFilePathForTest(TestDescription test) {
        return String.format("%s/%s__%s.ec", getCoverageDevicePath(),
                test.getClassName().replace(".", "_"), test.getTestName());
    }

    private String getCoverageDevicePath() {
        return String.format("/data/user/0/%s", mPackageName);
    }

    public String getMergedCoverageDevicePath() {
        return String.format("%s/%s", getCoverageDevicePath(), MERGED_COVERAGE_FILE);
    }

    public void mergeAllCoverageReports(ITestDevice device) throws DeviceNotAvailableException {
        try (AdbRootElevator root = new AdbRootElevator(device)) {
            CommandResult findFiles = device.executeShellV2Command(
                    String.format("find %s -name '*.ec'", getCoverageDevicePath()));
            if (!findFiles.getStatus().equals(CommandStatus.SUCCESS)
                    || findFiles.getStdout().isEmpty()) {
                throw new IllegalStateException(
                        String.format("Failed to find any coverage traces at %s",
                                getCoverageDevicePath()));
            }
            List<String> coverageReportsOnDevice = List.of(findFiles.getStdout().split("\n"));
            ExecFileLoader coverageMerger = new ExecFileLoader();
            for (String file : coverageReportsOnDevice) {
                try {
                    LogUtil.CLog.i("Loading %s", file);
                    coverageMerger.load(device.pullFile(file));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            deleteIndividualCoverageFiles(device);
            try {
                File mergedFileOnDesk = FileUtil.createTempFile("merged_java_coverage", ".ec");
                coverageMerger.save(mergedFileOnDesk, false);
                LogUtil.CLog.i("Merged all to %s", mergedFileOnDesk);
                device.pushFile(mergedFileOnDesk, getMergedCoverageDevicePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public HashMap<String, MetricMeasurement.Metric> createMetricMap() {
        MetricMeasurement.Metric.Builder metricBuilder = MetricMeasurement.Metric.newBuilder();
        metricBuilder.setMeasurements(metricBuilder.getMeasurementsBuilder().setSingleString(
                getMergedCoverageDevicePath()).build());
        HashMap<String, MetricMeasurement.Metric> coverageHashMap = new HashMap<>();
        coverageHashMap.put("coverageFilePath", metricBuilder.build());
        return coverageHashMap;
    }
}
