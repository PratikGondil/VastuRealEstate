package com.vastu.realestate.rootModule.splash.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.vastu.realestate.R
import com.vastu.realestate.appModule.activity.LoginActivity
import com.vastu.realestate.appModule.dashboard.view.BaseActivity
import com.vastu.realestate.appModule.dashboard.view.DashboardActivity
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            var isLogin:Boolean? = PreferenceManger.get<Boolean>(PreferenceKEYS.IS_LOGIN)
            if(isLogin == true)

                startActivity(Intent(this, DashboardActivity::class.java))
            else
                startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },500)
    }

}