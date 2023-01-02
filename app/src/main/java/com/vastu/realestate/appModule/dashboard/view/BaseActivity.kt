package com.vastu.realestate.appModule.dashboard.view

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Visibility
import com.google.android.material.bottomsheet.BottomSheetDialog
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
    open fun showDialog(message: String?,isSuccess:Boolean,isNetworkFailure:Boolean) {

        val bottomSheetDialog = BottomSheetDialog(this,android.R.style.Theme_Translucent_NoTitleBar)
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_layout)

        val successImageView = bottomSheetDialog.findViewById<CircleImageView>(R.id.success)
        val detailTextView =bottomSheetDialog.findViewById<TextView>(R.id.messageTextview)
        val okBtn = bottomSheetDialog.findViewById<Button>(R.id.btnOkay)

        if(isSuccess)
            successImageView?.visibility = View.VISIBLE
        else
            successImageView?.visibility = View.GONE



        if(isNetworkFailure){
            detailTextView?.text = getString(R.string.no_connection)
            okBtn?.visibility = View.GONE
        }else {
            okBtn?.visibility = View.VISIBLE
            detailTextView?.text = message
        }
        okBtn?.setOnClickListener { v: View? -> bottomSheetDialog.hide() }
        bottomSheetDialog.setCancelable(true)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        bottomSheetDialog.show()
    }
}