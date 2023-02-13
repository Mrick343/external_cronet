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

import android.content.Context;
import android.content.res.AssetManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Helper class to install test files.
 */
public final class TestFilesInstaller {
    private static final String INSTALLED_PATH_SUFFIX = "cronet_test_data";

    /**
     * Installs test files if files have not been installed.
     */
    public static void installIfNeeded(Context context) {
        File testDataDir = new File(context.getCacheDir(), INSTALLED_PATH_SUFFIX);

        if (testDataDir.exists()) {
            return;
        }

        AssetManager assets = context.getAssets();
        try {
            for (String assetPath : listAllAssets(assets)) {
                File copiedAssetFile = new File(testDataDir, assetPath);
                copiedAssetFile.getParentFile().mkdirs();
                try (InputStream inputStream = assets.open(assetPath)) {
                    Files.copy(inputStream, copiedAssetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            throw new AssertionError("Failed to copy test files", e);
        }
    }

    /**
     * Returns the installed path of the test files.
     */
    public static String getInstalledPath(Context context) {
        return new File(context.getCacheDir(), INSTALLED_PATH_SUFFIX).getAbsolutePath();
    }

    private static List<String> listAllAssets(AssetManager assets) throws IOException {
        Queue<String> toProcess = new ArrayDeque<>();
        toProcess.add("");
        List<String> result = new ArrayList<>();
        while (!toProcess.isEmpty()) {
            String parent = toProcess.remove();
            String[] children = assets.list(parent);
            if (children.length > 0) {
                // It's a folder
                for (String child : children) {
                    toProcess.add(new File(parent, child).toString());
                }
            } else if (!parent.isEmpty()) {
                // It's a file
                result.add(parent);
            } // Else it's the empty root folder, in which case do nothing
        }
        return result;
    }
}
