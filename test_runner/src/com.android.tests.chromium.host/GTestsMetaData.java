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

import com.android.tradefed.log.LogUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GTestsMetaData {
    private static class GTestMetaData {
        public String testSuite, testName, status, result;
        List<String> failures;

        public GTestMetaData(String testSuite, String testName, String status,
                List<String> failures) {
            this.testSuite = testSuite;
            this.testName = testName;
            this.status = status;
            this.result = failures.size() > 0 ? "FAILED" : "OK";
            this.failures = failures;
        }
    }

    private int totalTests, failedTests, disabledTests;
    private final List<GTestMetaData> testsMetaData;

    public GTestsMetaData(File gtestOutputFile) throws IOException {
        this.testsMetaData = new ArrayList<>();
        parseFile(gtestOutputFile);
    }

    public void parseFile(File gtestOutputFile) throws IOException {
        try (FileReader fileReader = new FileReader(gtestOutputFile)) {
            JsonElement root = JsonParser.parseReader(fileReader);
            if (root.getAsJsonObject() == null) {
                return;
            }
            this.totalTests = root.getAsJsonObject().get("tests").getAsInt();
            this.failedTests = root.getAsJsonObject().get("failures").getAsInt();
            this.disabledTests = root.getAsJsonObject().get("disabled").getAsInt();
            JsonArray testSuites = root.getAsJsonObject().get("testsuites").getAsJsonArray();
            for (JsonElement testSuite : testSuites) {
                String testSuiteName = testSuite.getAsJsonObject().get("name").getAsString();
                JsonArray tests = testSuite.getAsJsonObject().get("testsuite").getAsJsonArray();
                for (JsonElement test : tests) {
                    List<String> testFailures = new ArrayList<>();
                    JsonElement failures = test.getAsJsonObject().get("failures");
                    if (failures != null) {
                        for (JsonElement failure : failures.getAsJsonArray()) {
                            testFailures.add(
                                    failure.getAsJsonObject().get("failure").getAsString());
                        }
                    }
                    testsMetaData.add(new GTestMetaData(testSuiteName,
                            test.getAsJsonObject().get("name").getAsString(),
                            test.getAsJsonObject().get("status").getAsString(),
                            testFailures));
                }
            }
            testsMetaData.sort((o1, o2) -> o2.failures.size() - o1.failures.size());
        }
    }

    public boolean hasAnyFailures() {
        return this.failedTests > 0;
    }

    public int getTotalTests() {
        return this.totalTests;
    }

    public void logTestsOutput() {
        for (GTestMetaData test : testsMetaData) {
            LogUtil.CLog.i(String.format("-------- Individual Test Output ----------\n"
                            + "Test Suite: %s\n"
                            + "Test Nane: %s\n"
                            + "Test Status: %s\n"
                            + "test Result: %s\n", test.testSuite, test.testName, test.status,
                    test.result));
            for (String failure : test.failures) {
                LogUtil.CLog.i(String.format("Failure: %s\n", failure));
            }
        }
        LogUtil.CLog.i("=====================  SUMMARY  =====================\n");
        LogUtil.CLog.i(String.format("Total Tests: %s\n"
                + "Disabled Tests: %s\n"
                + "Failed Tests: %s\n", this.totalTests, this.disabledTests, this.failedTests));

    }
}
