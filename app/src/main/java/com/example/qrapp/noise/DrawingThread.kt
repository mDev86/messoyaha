package com.example.qrapp.noise

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.Log
import android.view.SurfaceHolder
import com.example.qrapp.App
import java.lang.Exception

class DrawingThread(holder: SurfaceHolder?, _params: DrawingParams) :Thread() {
    private val mSurfaceHolder: SurfaceHolder = holder!!

    private val mPaint: Paint = Paint()

    private var mRunning = false
    private var mPrevTime: Long = 0

    private val params = _params


    fun setRunning(running: Boolean){
        mRunning = running
        mPrevTime = getTime()
    }

    fun setPaintColor(color: Int){
        mPaint.color = color
    }

    fun getTime(): Long {
       return System.nanoTime() / 1_000_000
    }

    fun getArray(x: Int, y: Int): FloatArray{

//        val size =  x*y*PERCENT_NOISE/100/NOISE_WIDTH.toInt()
        val size =  x*y*params.PERCENT_NOISE/100/params.NOISE_WIDTH.toInt() //App.wPaint.toInt()

        val _x = FloatArray(size) { (0..x).random().toFloat() }
        val _y = FloatArray(size) { (0..y).random().toFloat() }

        var array = FloatArray(size * 2)

        for (l in 0 until size*2 step 2){
            array[l] = _x[l/2]
            array[l+1] = _y[l/2]
        }

        return array
    }

    override fun run() {
        while(mRunning){
            var canvas: Canvas? = null
            var curTime = getTime()
            if(curTime - mPrevTime < params.REDRAW_TIME) continue
            try{
                sleep(1)

                canvas = mSurfaceHolder.lockCanvas()
                synchronized(mSurfaceHolder){
                    canvas?.drawColor(Color.WHITE)
                    canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                    mPaint.strokeWidth = params.NOISE_WIDTH
                    canvas?.drawPoints(getArray(canvas?.width, canvas?.height), mPaint)
                }

            }catch (e: NullPointerException){}
            finally {
                if(canvas != null){
                    mSurfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
            mPrevTime = curTime
        }
    }
}