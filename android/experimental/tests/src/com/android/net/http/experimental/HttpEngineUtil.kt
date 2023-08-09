package com.android.net.http.experimental

import android.net.http.UrlRequest
import android.net.http.HttpException
import android.util.Log
import android.os.Debug
import java.util.concurrent.Executors
import android.net.http.UrlResponseInfo
import java.nio.ByteBuffer
import android.content.Context
import android.net.http.HttpEngine

private const val TAG = "ZygoteMemoryExperiments"

fun doUrlRequest(context: Context, url: String) {
    val mEngineBuilder = HttpEngine.Builder(context);
    val mEngine = mEngineBuilder.build()
    val mCallback = MyCallback()
    val builder =
                mEngine.newUrlRequestBuilder(url, Executors.newSingleThreadExecutor(), mCallback)
    val mRequest = builder.build()
    mRequest.start()
}

fun loadLibrary() {
      Debug.traceMemoryFootprintBegin("doPreload");
      Debug.traceMemoryFootprintBegin("System.loadLibrary");
      System.loadLibrary("cronet.114.0.5735.84");
      Debug.traceMemoryFootprintEnd();
      Debug.traceMemoryFootprintEnd();
}

class MyCallback : UrlRequest.Callback {
        override fun onRedirectReceived(
                request: UrlRequest, info: UrlResponseInfo, newLocationUrl: String) {
          Log.i(TAG, "onRedirectReceived")
          request.followRedirect()
        }


        override fun onResponseStarted(request: UrlRequest, info: UrlResponseInfo) {
          Log.i(TAG, "onResponseStarted")
          request.read(ByteBuffer.allocateDirect(1024))
        }

        override fun onReadCompleted(
                request: UrlRequest, info: UrlResponseInfo, byteBuffer: ByteBuffer) {
          Log.i(TAG, "onReadCompleted")
          request.read(ByteBuffer.allocateDirect(1024))
        }

        override fun onSucceeded(request: UrlRequest, info: UrlResponseInfo) {
          Log.i(TAG, "onReadCompleted")
        }

        override fun onFailed(
                request: UrlRequest, info: UrlResponseInfo?, error: HttpException) {
          Log.i(TAG, "onFailed: ", error)
        }

        override fun onCanceled(request: UrlRequest, info: UrlResponseInfo?) {
          Log.i(TAG, "onCanceled")
        }
    }
