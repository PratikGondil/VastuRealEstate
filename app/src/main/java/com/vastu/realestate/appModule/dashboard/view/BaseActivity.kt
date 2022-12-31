package com.vastu.realestate.appModule.dashboard.view

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.vastu.realestate.R
import com.vastu.realestate.customProgressDialog.CustomProgressDialog
import de.hdodenhof.circleimageview.CircleImageView

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
    open fun showDialog(message: String?,isSuccess:Boolean) {

        val fullScreenInformationDialog = Dialog(this)
        fullScreenInformationDialog.setContentView(R.layout.bottom_dialog_layout)

        val successImageView:CircleImageView = fullScreenInformationDialog.findViewById<CircleImageView>(R.id.success)
        val detailTextView: TextView =fullScreenInformationDialog.findViewById<TextView>(R.id.messageTextview)

        if(isSuccess)
            successImageView.visibility = View.VISIBLE
        else
            successImageView.visibility = View.GONE

        val okBtn: Button = fullScreenInformationDialog.findViewById<Button>(R.id.btnOkay)
        detailTextView.text = message
        okBtn.setOnClickListener { v: View? -> fullScreenInformationDialog.hide() }
        fullScreenInformationDialog.setCancelable(true)
        fullScreenInformationDialog.setCanceledOnTouchOutside(true)
        fullScreenInformationDialog.show()
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