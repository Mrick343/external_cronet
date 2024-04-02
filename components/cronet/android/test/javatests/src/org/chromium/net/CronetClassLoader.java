// Copyright 2014 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net;

import static com.google.common.truth.Truth.assertThat;

import static org.chromium.net.CronetEngine.Builder.HTTP_CACHE_IN_MEMORY;
import static org.chromium.net.CronetTestRule.getTestStorage;
import static org.chromium.net.truth.UrlResponseInfoSubject.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assume.assumeTrue;

import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.android.testutils.SkipPresubmit;

import dalvik.system.DelegateLastClassLoader;
import dalvik.system.PathClassLoader;

import org.chromium.base.Log;
import org.chromium.base.PathUtils;
import org.chromium.base.test.util.DoNotBatch;
import org.chromium.net.CronetTestRule.CronetImplementation;
import org.chromium.net.CronetTestRule.DisableAutomaticNetLog;
import org.chromium.net.CronetTestRule.IgnoreFor;
import org.chromium.net.CronetTestRule.RequiresMinAndroidApi;
import org.chromium.net.CronetTestRule.RequiresMinApi;
import org.chromium.net.NetworkChangeNotifierAutoDetect.ConnectivityManagerDelegate;
import org.chromium.net.TestUrlRequestCallback.ResponseStep;
import org.chromium.net.httpflags.BaseFeature;
import org.chromium.net.httpflags.FlagValue;
import org.chromium.net.httpflags.Flags;
import org.chromium.net.impl.CronetLibraryLoader;
import org.chromium.net.impl.CronetManifest;
import org.chromium.net.impl.CronetManifestInterceptor;
import org.chromium.net.impl.CronetUrlRequestContext;
import org.chromium.net.impl.ImplVersion;
import org.chromium.net.impl.NativeCronetEngineBuilderImpl;
import org.chromium.net.impl.NetworkExceptionImpl;
import org.jni_zero.JNINamespace;
import org.jni_zero.NativeMethods;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

public class CronetClassLoader extends BlockJUnit4ClassRunner {

    private static ClassLoader customClassLoader;

    public CronetClassLoader(Class<?> testClass) throws InitializationError {
        super(loadFromCustomClassloader(testClass));
        Log.i("hehe", "Loaded %s", testClass.getName());
    }

    // Loads a class in the custom classloader
    private static Class<?> loadFromCustomClassloader(Class<?> clazz) throws InitializationError {
        try {
            // Only load once to support parallel tests
            if (customClassLoader == null) {
                customClassLoader =
                        new LoadFirstClassLoader(
                                ApplicationProvider.getApplicationContext()
                                        .getApplicationInfo()
                                        .sourceDir,
                                String.format("%s!/lib/%s", ApplicationProvider.getApplicationContext().getApplicationInfo().sourceDir,
                                        ApplicationProvider.getApplicationContext().getApplicationInfo().primaryCpuAbi),
                                CronetClassLoader.class.getClassLoader());
            }
            Log.i("hihi", ApplicationProvider.getApplicationContext().getApplicationInfo().nativeLibraryDir);
            Log.i("hehe", ApplicationProvider.getApplicationContext()
                    .getApplicationInfo()
                    .sourceDir);
            return customClassLoader.loadClass(clazz.getName());
           //  return Class.forName(clazz.getName(), true, customClassLoader);
        } catch (ClassNotFoundException e) {
            throw new InitializationError(e);
        }
    }

    // Runs junit tests in a separate thread using the custom class loader
    @Override
    public void run(final RunNotifier notifier) {
        Runnable runnable =
                () -> super.run(notifier);
        Thread thread = new Thread(runnable);
        thread.setContextClassLoader(customClassLoader);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class LoadFirstClassLoader extends PathClassLoader {

        public LoadFirstClassLoader(String dexPath, ClassLoader parent) {
            super(dexPath, parent);
        }

        public LoadFirstClassLoader(String dexPath, String librarySearchPath, ClassLoader parent) {
            super(dexPath, librarySearchPath, parent);
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            // First, check whether the class has already been loaded. Return it if that's the
            // case.
            Log.i("CronetClassLoader", "Trying to load " + name);
            Class<?> cl = findLoadedClass(name);
            if (cl != null) {
                return cl;
            }

            if(name.startsWith("android.net")) {
                // Next, check whether the class in question is present in the dexPath that this
                // classloader
                // operates on, or its shared libraries.
                ClassNotFoundException fromSuper = null;
                try {
                    return findClass(name);
                } catch (ClassNotFoundException ex) {
                    fromSuper = ex;
                }

                // Next, check whether the class in question is present in the boot classpath.
                try {
                    return Object.class.getClassLoader().loadClass(name);
                } catch (ClassNotFoundException ignored) {
                }
                // Finally, check whether the class in question is present in the parent classloader.
                try {
                    return getParent().loadClass(name);
                } catch (ClassNotFoundException cnfe) {
                    // The exception we're catching here is the CNFE thrown by the parent of this
                    // classloader. However, we would like to throw a CNFE that provides details about
                    // the class path / list of dex files associated with *this* classloader, so we
                    // choose
                    // to throw the exception thrown from that lookup.
                    throw fromSuper;
                }
            } else {
                return super.loadClass(name, resolve);
            }
        }

        @Override
        public URL getResource(String name) {
            // The lookup order we use here is the same as for classes.
            URL resource = findResource(name);
            if (resource != null) {
                return resource;
            }

            resource = Object.class.getClassLoader().getResource(name);
            if (resource != null) {
                return resource;
            }

            return getParent().getResource(name);
        }

        @Override
        public Enumeration<URL> getResources(String name) throws IOException {
            @SuppressWarnings("unchecked")
            final Enumeration<URL>[] resources =
                    (Enumeration<URL>[])
                            new Enumeration<?>[] {
                                findResources(name),
                                Object.class.getClassLoader().getResources(name),
                                getParent().getResources(name)
                            };

            return new CompoundEnumeration<>(resources);
        }
    }

    private static class CompoundEnumeration<E> implements Enumeration<E> {
        private Enumeration<E>[] enums;
        private int index = 0;

        public CompoundEnumeration(Enumeration<E>[] enums) {
            this.enums = enums;
        }

        private boolean next() {
            while (index < enums.length) {
                if (enums[index] != null && enums[index].hasMoreElements()) {
                    return true;
                }
                index++;
            }
            return false;
        }

        public boolean hasMoreElements() {
            return next();
        }

        public E nextElement() {
            if (!next()) {
                throw new NoSuchElementException();
            }
            return enums[index].nextElement();
        }
    }
}
