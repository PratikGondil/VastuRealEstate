package com.vastu.realestate.appModule.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.vastu.realestate.R
import com.vastu.realestate.appModule.otp.fragment.OTPFragment


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.login_container, LoginFragment())
//        transaction.commit()
    }

    override fun onBackPressed() {
        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.loginNavHost) as NavHostFragment?
        val fragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (fragment is OTPFragment)
        {
            super.onBackPressed()
        }
        else(finishAffinity()
        )
    }
}