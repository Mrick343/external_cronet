// Copyright 2016 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net.impl;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

<<<<<<< HEAD   (12482f Merge remote-tracking branch 'aosp/master' into upstream-sta)
import android.net.http.HttpException;
import android.net.http.RequestFinishedInfo;
import android.net.http.UrlResponseInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Collections;

/**
 * Implements information about a finished request. Passed to {@link RequestFinishedInfo.Listener}.
 */
public class RequestFinishedInfoImpl extends RequestFinishedInfo {
    private final String mUrl;
    private final Collection<Object> mAnnotations;
    private final RequestFinishedInfo.Metrics mMetrics;

    @FinishedReason
    private final int mFinishedReason;

    @Nullable
    private final UrlResponseInfo mResponseInfo;
    @Nullable
    private final HttpException mException;

    @IntDef({SUCCEEDED, FAILED, CANCELED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FinishedReason {}

    public RequestFinishedInfoImpl(String url, Collection<Object> annotations,
            RequestFinishedInfo.Metrics metrics, @FinishedReason int finishedReason,
            @Nullable UrlResponseInfo responseInfo, @Nullable HttpException exception) {
        mUrl = url;
        mAnnotations = annotations;
        mMetrics = metrics;
        mFinishedReason = finishedReason;
        mResponseInfo = responseInfo;
        mException = exception;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public Collection<Object> getAnnotations() {
        if (mAnnotations == null) {
            return Collections.emptyList();
        }
        return mAnnotations;
    }

    @Override
    public Metrics getMetrics() {
        return mMetrics;
    }

    @Override
    @FinishedReason
    public int getFinishedReason() {
        return mFinishedReason;
    }

    @Override
    @Nullable
    public UrlResponseInfo getResponseInfo() {
        return mResponseInfo;
    }

    @Override
    @Nullable
    public HttpException getException() {
=======
import org.chromium.net.CronetException;
import org.chromium.net.RequestFinishedInfo;
import org.chromium.net.UrlResponseInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Collections;

/**
 * Implements information about a finished request. Passed to {@link RequestFinishedInfo.Listener}.
 */
public class RequestFinishedInfoImpl extends RequestFinishedInfo {
    private final String mUrl;
    private final Collection<Object> mAnnotations;
    private final RequestFinishedInfo.Metrics mMetrics;

    @FinishedReason
    private final int mFinishedReason;

    @Nullable
    private final UrlResponseInfo mResponseInfo;
    @Nullable
    private final CronetException mException;

    @IntDef({SUCCEEDED, FAILED, CANCELED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FinishedReason {}

    public RequestFinishedInfoImpl(String url, Collection<Object> annotations,
            RequestFinishedInfo.Metrics metrics, @FinishedReason int finishedReason,
            @Nullable UrlResponseInfo responseInfo, @Nullable CronetException exception) {
        mUrl = url;
        mAnnotations = annotations;
        mMetrics = metrics;
        mFinishedReason = finishedReason;
        mResponseInfo = responseInfo;
        mException = exception;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public Collection<Object> getAnnotations() {
        if (mAnnotations == null) {
            return Collections.emptyList();
        }
        return mAnnotations;
    }

    @Override
    public Metrics getMetrics() {
        return mMetrics;
    }

    @Override
    @FinishedReason
    public int getFinishedReason() {
        return mFinishedReason;
    }

    @Override
    @Nullable
    public UrlResponseInfo getResponseInfo() {
        return mResponseInfo;
    }

    @Override
    @Nullable
    public CronetException getException() {
>>>>>>> BRANCH (26b171 Part 2 of Import Cronet version 108.0.5359.128)
        return mException;
    }
}
