package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vastu.realestate.customProgressDialog.CustomProgressDialog

open class BaseActivity : AppCompatActivity() {

    private lateinit var customProgressDialog : CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customProgressDialog = CustomProgressDialog.getInstance()
    }

    fun showProgressDialog(){
        customProgressDialog.show(this)
    }
    fun hideProgressDialog() {
        customProgressDialog.dismiss()
    }

    fun onBackNavigation() {
        try {
            if (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
            } else {
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}