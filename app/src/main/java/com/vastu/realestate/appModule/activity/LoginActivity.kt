package com.vastu.realestate.appModule.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vastu.realestate.R
import com.vastu.realestate.appModule.login.view.fragment.LoginFragment


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.login_container, LoginFragment())
//        transaction.commit()
    }
}