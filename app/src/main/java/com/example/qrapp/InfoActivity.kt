package com.example.qrapp

import android.graphics.PixelFormat
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import com.example.qrapp.model.Profile
import com.example.qrapp.noise.CustomSurfaceView
import com.example.qrapp.noise.DrawingParams
import kotlinx.android.synthetic.main.activity_info.*
import java.text.SimpleDateFormat

class InfoActivity : AppCompatActivity() {

    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd.MM.yyyy")
    val formatter1 = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        EnableSecurity()

        val profile = intent.getParcelableExtra<Profile>("profile")

        //Основные данные
        passNum.text = profile.barcode
        passDate.text = "c ${formatter.format( parser.parse(profile.startDate))} по ${formatter.format( parser.parse(profile.endDate))}"
        passFio.text = "${profile.lastName} ${profile.firstName} ${profile.middleName}"
        passPosition.text = "${profile.position1}${if(profile.position2 !=null) " (${profile.position2})" else ""}"
        passCompany.text = profile.companyName
        passSubCompany.text = profile.subcontractorName
        passAccess.text = profile.objectName

        //Документы
        docList.layoutManager = LinearLayoutManager(applicationContext)
        docList.adapter = RVDocAdapter(profile.documents)

        //Проверка недействительности пропуска
        if(profile.isInvalid()){
            layoutInfo.background = getDrawable(R.drawable.pass_bad)
            invalidDate.text = "${formatter1.format( parser.parse(profile.getInvalidDate()))}"
            invalidCard.visibility = View.VISIBLE
        }



        /*seekNoise.progress = App.noise
        seekNoise.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                App.noise = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                  }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
        })
        seekPaint.progress = App.wPaint.toInt()
        seekPaint.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                App.wPaint = progress.toFloat()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        seekRedraw.progress = App.Redraw
        seekRedraw.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                App.Redraw = progress
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })*/
    }


    fun EnableSecurity(){
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        passNumNoise.setZOrderOnTop(true)
        passNumNoise.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passNumNoise.holder.setFormat(PixelFormat.TRANSPARENT)
        passNumNoise1.setZOrderOnTop(true)
        passNumNoise1.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passNumNoise1.holder.setFormat(PixelFormat.TRANSPARENT)

        passDateNoise.setZOrderOnTop(true)
        passDateNoise.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passDateNoise.holder.setFormat(PixelFormat.TRANSPARENT)
        passDateNoise1.setZOrderOnTop(true)
        passDateNoise1.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passDateNoise1.holder.setFormat(PixelFormat.TRANSPARENT)

        passFioNoise.setZOrderOnTop(true)
        passFioNoise.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passFioNoise.holder.setFormat(PixelFormat.TRANSPARENT)
        passFioNoise1.setZOrderOnTop(true)
        passFioNoise1.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passFioNoise1.holder.setFormat(PixelFormat.TRANSPARENT)

        passPositionNoise.setZOrderOnTop(true)
        passPositionNoise.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passPositionNoise.holder.setFormat(PixelFormat.TRANSPARENT)
        passPositionNoise1.setZOrderOnTop(true)
        passPositionNoise1.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passPositionNoise1.holder.setFormat(PixelFormat.TRANSPARENT)

        passCompanyNoise.setZOrderOnTop(true)
        passCompanyNoise.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passCompanyNoise.holder.setFormat(PixelFormat.TRANSPARENT)
        passCompanyNoise1.setZOrderOnTop(true)
        passCompanyNoise1.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passCompanyNoise1.holder.setFormat(PixelFormat.TRANSPARENT)

        passSubCompanyNoise.setZOrderOnTop(true)
        passSubCompanyNoise.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passSubCompanyNoise.holder.setFormat(PixelFormat.TRANSPARENT)
        passSubCompanyNoise1.setZOrderOnTop(true)
        passSubCompanyNoise1.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passSubCompanyNoise1.holder.setFormat(PixelFormat.TRANSPARENT)

        passAccessNoise.setZOrderOnTop(true)
        passAccessNoise.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passAccessNoise.holder.setFormat(PixelFormat.TRANSPARENT)
        passAccessNoise1.setZOrderOnTop(true)
        passAccessNoise1.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardOk), DrawingParams()))
        passAccessNoise1.holder.setFormat(PixelFormat.TRANSPARENT)

        docListNoise.setZOrderOnTop(true)
        docListNoise.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorWhite), DrawingParams(PERCENT_NOISE = 5, NOISE_WIDTH = 8.0F)))
        docListNoise.holder.setFormat(PixelFormat.TRANSPARENT)
        docListNoise1.setZOrderOnTop(true)
        docListNoise1.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorWhite), DrawingParams(PERCENT_NOISE = 5, NOISE_WIDTH = 8.0F)))
        docListNoise1.holder.setFormat(PixelFormat.TRANSPARENT)

        alertNoise.setZOrderOnTop(true)
        alertNoise.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardBad), DrawingParams()))
        alertNoise.holder.setFormat(PixelFormat.TRANSPARENT)
        alertNoise1.setZOrderOnTop(true)
        alertNoise1.holder.addCallback(CustomSurfaceView(ContextCompat.getColor(applicationContext, R.color.colorCardBad), DrawingParams()))
        alertNoise1.holder.setFormat(PixelFormat.TRANSPARENT)

    }
}
