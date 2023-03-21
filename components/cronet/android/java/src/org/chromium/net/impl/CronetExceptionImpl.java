// Copyright 2016 The Chromium Authors
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.net.impl;

<<<<<<< HEAD   (a4cf74 Merge remote-tracking branch 'aosp/master' into upstream-sta)
import android.net.http.HttpException;

/**
 * Implements {@link HttpException}.
 */
public class CronetExceptionImpl extends HttpException {
=======
import org.chromium.net.CronetException;

/**
 * Implements {@link CronetException}.
 */
public class CronetExceptionImpl extends CronetException {
>>>>>>> BRANCH (14c906 Import Cronet version 108.0.5359.128)
    public CronetExceptionImpl(String message, Throwable cause) {
        super(message, cause);
    }
}
