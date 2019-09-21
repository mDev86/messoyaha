package com.example.qrapp

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.example.qrapp.noise.CustomSurfaceView
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {
            val quickPermissionsOption = QuickPermissionsOptions(
                rationaleMethod = { req -> rationaleCallback(req) }
            )
            runWithPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                options = quickPermissionsOption){
                    App.recursiveSD = RecursiveFileObserver("/sdcard").startWatching()
                    App.recursiveStore = RecursiveFileObserver("/storage").startWatching()
//                    Thread.sleep(1000L)
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                }
        }

    }

    private fun rationaleCallback(req: QuickPermissionsRequest) {
        // this will be called when permission is denied once or more time. Handle it your way
        AlertDialog.Builder(this, R.style.AlertDialogCustom)
            .setTitle("Отсутствуют разрешения")
            .setMessage("Для корректной работы приложения требуются соответствующие разрешения")
            .setPositiveButton("Повторить") { dialog, which -> req.proceed() }
            .setNegativeButton("Выход") { dialog, which -> finish() }
            .setCancelable(false)
            .show()
    }
}
