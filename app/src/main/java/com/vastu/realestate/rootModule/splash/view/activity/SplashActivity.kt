package com.vastu.realestate.rootModule.splash.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vastu.realestate.appModule.activity.LoginActivity
import com.vastu.realestate.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}