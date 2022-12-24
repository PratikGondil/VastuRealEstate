package com.vastu.realestate.customProgressDialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.vastu.realestate.R


object CustomProgressDialog{

    private var dialog: Dialog? = null
    private val ourInstance: CustomProgressDialog = CustomProgressDialog
    fun getInstance(): CustomProgressDialog{
        return ourInstance
    }

    fun show(context: Context?) {
        if (dialog != null && dialog!!.isShowing) {
            return
        }
        dialog = Dialog(context!!)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.progress_dialog)
        dialog!!.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }
}