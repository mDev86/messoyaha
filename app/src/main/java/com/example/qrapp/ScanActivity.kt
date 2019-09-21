package com.example.qrapp

import android.content.Context
import android.content.Intent
import android.os.*
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.qrapp.model.Profile
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_info.view.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScanActivity : AppCompatActivity() {

    private val codeLength = 7

    private var mCode = ""
        set(value) {
            field = value.take(codeLength)
            reDrawCode()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        btnScan.setOnClickListener {
            run{
                val integrator = IntentIntegrator(this@ScanActivity)
                integrator.setBarcodeImageEnabled(false)
                integrator.setPrompt("Сканируйте штрих код")
                integrator.setBeepEnabled(false)
                integrator.initiateScan()
            }
        }

        btnDel.setOnClickListener {
            mCode = mCode.dropLast(1)
        }


    }

    override fun onStart() {
        super.onStart()
        scanLoader.visibility = View.GONE
        mCode = ""
    }

    fun onClickNum(view: View){
        mCode = mCode.plus((view as TextView).text.toString())
    }


    fun reDrawCode(){
        var tmp = mCode
        while(tmp.length < codeLength) tmp = tmp.plus("\u2022")
        tvCode.text = tmp

        if(mCode.length == codeLength){
            scanLoader.visibility = View.VISIBLE
            getInfoByCode(mCode)
        }
    }


    fun getInfoByCode(code: String){
        GlobalScope.launch {
            AndroidNetworking.get("https://student.i-propusk.ru/api/Passes/WorkerPasses/${code}C")
                .build()
                .getAsObject(Profile::class.java, object : ParsedRequestListener<Profile> {
                    override fun onResponse(response: Profile?) {
                        scanLoader.visibility = View.VISIBLE
                        var data = Intent(this@ScanActivity, InfoActivity::class.java)
                        data.putExtra("profile", response)
                        startActivity(data)
                    }

                    override fun onError(anError: ANError?) {
                        var error = if (anError?.errorCode == 404) "Пропуск не найден" else anError?.errorBody
                        Toast.makeText(this@ScanActivity, "Ошибка получения данных:\n$error", Toast.LENGTH_SHORT).show()
                        scanLoader.visibility = View.GONE
                        mCode = ""
                    }

                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null){
            if(result.contents != null){
                val vib: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    vib.vibrate(
                        VibrationEffect.createOneShot(
                            100,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                }else{
                    vib.vibrate(100)
                }

                mCode = result.contents
            } else {

            }
        } else {
            Toast.makeText(this@ScanActivity, "Ошибка сканирования", Toast.LENGTH_SHORT).show()
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private var back_pressed: Long = 0
    override fun onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            startActivity(Intent(this@ScanActivity, LoginActivity::class.java))
        } else {
            Snackbar.make(btnScan, "Нажмите ещё раз для выхода из профиля", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        back_pressed = System.currentTimeMillis()
    }

}


