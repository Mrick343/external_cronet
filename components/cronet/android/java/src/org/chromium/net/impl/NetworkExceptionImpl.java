// Copyright 2016 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net.impl;

<<<<<<< HEAD   (a4cf74 Merge remote-tracking branch 'aosp/master' into upstream-sta)
import android.net.http.NetworkException;

/**
 * Implements {@link NetworkException}.
 */
public class NetworkExceptionImpl extends NetworkException {
    // Error code, one of ERROR_*
    protected final int mErrorCode;
    // Cronet internal error code.
    protected final int mCronetInternalErrorCode;

    /**
     * Constructs an exception with a specific error.
     *
     * @param message explanation of failure.
     * @param errorCode error code, one of {@link #ERROR_HOSTNAME_NOT_RESOLVED ERROR_*}.
     * @param cronetInternalErrorCode Cronet internal error code, one of
     * <a href=https://chromium.googlesource.com/chromium/src/+/main/net/base/net_error_list.h>
     * these</a>.
     */
    public NetworkExceptionImpl(String message, int errorCode, int cronetInternalErrorCode) {
        super(message, null);
        assert errorCode > 0 && errorCode < 12;
        assert cronetInternalErrorCode < 0;
        mErrorCode = errorCode;
        mCronetInternalErrorCode = cronetInternalErrorCode;
    }

    @Override
    public int getErrorCode() {
        return mErrorCode;
    }

    @Override
    public int getInternalErrorCode() {
        return mCronetInternalErrorCode;
    }

    @Override
    public boolean isImmediatelyRetryable() {
        switch (mErrorCode) {
            case ERROR_HOSTNAME_NOT_RESOLVED:
            case ERROR_INTERNET_DISCONNECTED:
            case ERROR_CONNECTION_REFUSED:
            case ERROR_ADDRESS_UNREACHABLE:
            case ERROR_OTHER:
            default:
                return false;
            case ERROR_NETWORK_CHANGED:
            case ERROR_TIMED_OUT:
            case ERROR_CONNECTION_CLOSED:
            case ERROR_CONNECTION_TIMED_OUT:
            case ERROR_CONNECTION_RESET:
                return true;
        }
    }

    @Override
    public String getMessage() {
        StringBuilder b = new StringBuilder(super.getMessage());
        b.append(", ErrorCode=").append(mErrorCode);
        if (mCronetInternalErrorCode != 0) {
            b.append(", InternalErrorCode=").append(mCronetInternalErrorCode);
        }
        b.append(", Retryable=").append(isImmediatelyRetryable());
=======
import org.chromium.net.NetworkException;

/**
 * Implements {@link NetworkException}.
 */
public class NetworkExceptionImpl extends NetworkException {
    // Error code, one of ERROR_*
    protected final int mErrorCode;
    // Cronet internal error code.
    protected final int mCronetInternalErrorCode;

    /**
     * Constructs an exception with a specific error.
     *
     * @param message explanation of failure.
     * @param errorCode error code, one of {@link #ERROR_HOSTNAME_NOT_RESOLVED ERROR_*}.
     * @param cronetInternalErrorCode Cronet internal error code, one of
     * <a href=https://chromium.googlesource.com/chromium/src/+/main/net/base/net_error_list.h>
     * these</a>.
     */
    public NetworkExceptionImpl(String message, int errorCode, int cronetInternalErrorCode) {
        super(message, null);
        assert errorCode > 0 && errorCode < 12;
        assert cronetInternalErrorCode < 0;
        mErrorCode = errorCode;
        mCronetInternalErrorCode = cronetInternalErrorCode;
    }

    @Override
    public int getErrorCode() {
        return mErrorCode;
    }

    @Override
    public int getCronetInternalErrorCode() {
        return mCronetInternalErrorCode;
    }

    @Override
    public boolean immediatelyRetryable() {
        switch (mErrorCode) {
            case ERROR_HOSTNAME_NOT_RESOLVED:
            case ERROR_INTERNET_DISCONNECTED:
            case ERROR_CONNECTION_REFUSED:
            case ERROR_ADDRESS_UNREACHABLE:
            case ERROR_OTHER:
            default:
                return false;
            case ERROR_NETWORK_CHANGED:
            case ERROR_TIMED_OUT:
            case ERROR_CONNECTION_CLOSED:
            case ERROR_CONNECTION_TIMED_OUT:
            case ERROR_CONNECTION_RESET:
                return true;
        }
    }

    @Override
    public String getMessage() {
        StringBuilder b = new StringBuilder(super.getMessage());
        b.append(", ErrorCode=").append(mErrorCode);
        if (mCronetInternalErrorCode != 0) {
            b.append(", InternalErrorCode=").append(mCronetInternalErrorCode);
        }
        b.append(", Retryable=").append(immediatelyRetryable());
>>>>>>> BRANCH (14c906 Import Cronet version 108.0.5359.128)
        return b.toString();
    }
}
