// Copyright 2017 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net.impl;

import androidx.annotation.VisibleForTesting;

import org.chromium.net.NetError;

/**
 * Used in {@link CronetBidirectionalStream}. Implements {@link NetworkExceptionImpl}.
 */
@VisibleForTesting
public class BidirectionalStreamNetworkException extends NetworkExceptionImpl {
    public BidirectionalStreamNetworkException(
            String message, int errorCode, int cronetInternalErrorCode) {
        super(message, errorCode, cronetInternalErrorCode);
    }

    @Override
<<<<<<< HEAD   (a4cf74 Merge remote-tracking branch 'aosp/master' into upstream-sta)
    public boolean isImmediatelyRetryable() {
        switch (mCronetInternalErrorCode) {
            case NetError.ERR_HTTP2_PING_FAILED:
            case NetError.ERR_QUIC_HANDSHAKE_FAILED:
                assert mErrorCode == ERROR_OTHER;
                return true;
            default:
                return super.isImmediatelyRetryable();
=======
    public boolean immediatelyRetryable() {
        switch (mCronetInternalErrorCode) {
            case NetError.ERR_HTTP2_PING_FAILED:
            case NetError.ERR_QUIC_HANDSHAKE_FAILED:
                assert mErrorCode == ERROR_OTHER;
                return true;
            default:
                return super.immediatelyRetryable();
>>>>>>> BRANCH (14c906 Import Cronet version 108.0.5359.128)
        }
    }
}
