package com.example.qrapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        startActivity(Intent(this@LoginActivity, ScanActivity::class.java))

        loginAddress.setText("https://student.i-propusk.ru/api/account/token")
        loginPassword.setText("6aoQRuIyyu")
        loginEmail.setText("a@i.ru")

        loginAddress.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                isValidAdress()
            }
        })

        loginEmail.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                isValidEmail()
            }
        })

        loginPassword.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                isValidPass()
            }
        })


        loginConnect.setOnClickListener {
            if(isValidForm()) {
                loginLoader.visibility = View.VISIBLE
                GlobalScope.launch {
                    AndroidNetworking.post(loginAddress.text.toString())
                        .addBodyParameter("grant_type", "password")
                        .addBodyParameter("username", loginEmail.text.toString())
                        .addBodyParameter("password", loginPassword.text.toString())
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject?) {
                                if (response?.has("access_token")!!) {
                                    RESTClient.setToken(response["access_token"].toString())
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Вход выполнен ${if (response?.has("username")!!) response["username"].toString() else ""}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    startActivity(Intent(this@LoginActivity, ScanActivity::class.java))
                                } else {
                                    Toast.makeText(this@LoginActivity, "Ошибка: токен не получен", Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onError(anError: ANError?) {
                                Toast.makeText(this@LoginActivity, "Ошибка: ${anError?.errorBody}", Toast.LENGTH_LONG)
                                    .show()
                                loginLoader.visibility = View.GONE
                            }

                        })
                }
            }
        }

    }

    fun isValidForm():Boolean{
        return isValidAdress() && isValidEmail() && isValidPass()
    }

    fun isValidEmail():Boolean
            = loginEmail.validator()
                .nonEmpty()
                //.validEmail()
                .addSuccessCallback {
                    loginEmail.error = null
                }
                .addErrorCallback {
                    loginEmail.error = "Некорректный email"
                }
                .check()

    fun isValidPass():Boolean
            = loginPassword.validator()
                .nonEmpty()
                .addSuccessCallback {
                    loginPassword.error = null
                }
                .addErrorCallback {
                    loginPassword.error = "Пароль не должен быть пустым"
                }
                .check()

    fun isValidAdress():Boolean
            = loginAddress.validator()
                .nonEmpty()
                .validUrl()
                .addSuccessCallback {
                    loginAddress.error = null
                }
                .addErrorCallback {
                    loginAddress.error = "Некорректный адрес подключения"
                }
                .check()


    private var back_pressed: Long = 0
    override fun onBackPressed() {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                finishAffinity()
            } else {
                Snackbar.make(loginConnect, "Нажмите ещё раз для закрытия прилолжения", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
            back_pressed = System.currentTimeMillis()
    }


}
