package com.android.net.http.experimental

import android.content.pm.ApplicationInfo
import android.util.Log
import android.os.Debug

/**
 *
 */
class ZygotePreloadClass : android.app.ZygotePreload{

  override fun doPreload(appInfo: ApplicationInfo) {
      loadLibrary()
  }
}
