package com.example.qrapp.noise

import android.util.Log
import android.view.SurfaceHolder


class CustomSurfaceView(private val paintColor: Int, private val params: DrawingParams): SurfaceHolder.Callback {
    lateinit var  mThread: DrawingThread

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
//        Log.d("Thread", "stop ${mThread.id.toString()}")
        mThread?.setRunning(false)
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mThread = DrawingThread(holder, params)
//        Log.d("Thread", "start ${mThread.id.toString()}")
        mThread?.setRunning(true)
        mThread?.setPaintColor(paintColor)
        mThread?.start()

    }


}

