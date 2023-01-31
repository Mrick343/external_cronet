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

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GTestsMetaData {

    private boolean isOutputParsedCorrectly;
    private int totalTests;
    private int failedTests;

    private GTestsMetaData() {

    }

    public static GTestsMetaData from(File gtestOutputFile) throws IOException {
        GTestsMetaData metaData = new GTestsMetaData();
        metaData.parseFile(gtestOutputFile);
        return metaData;
    }

    public boolean isOutputParsedCorrectly() {
        return isOutputParsedCorrectly;
    }

    private boolean parseFile(File gtestOutputFile) throws IOException {
        try (FileReader fileReader = new FileReader(gtestOutputFile)) {
            JsonElement root = JsonParser.parseReader(fileReader);
            if (!root.isJsonObject()) {
                return false;
            }
            totalTests = root.getAsJsonObject().get("tests").getAsInt();
            failedTests = root.getAsJsonObject().get("failures").getAsInt();
            isOutputParsedCorrectly = true;
            return true;
        }
    }

    public boolean hasAnyFailures() {
        return this.failedTests > 0;
    }

    public int getTotalTests() {
        return this.totalTests;
    }
}
