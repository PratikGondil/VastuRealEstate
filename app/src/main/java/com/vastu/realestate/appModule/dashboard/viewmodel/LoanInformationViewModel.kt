package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.appModule.dashboard.uiInterfaces.ILoanInfoListener

class LoanInformationViewModel(application: Application) :AndroidViewModel(application) {
    lateinit var iLoanInfoListener:ILoanInfoListener
    var maxline=ObservableField(16)
    var btnText=ObservableField("")
    var mainMorebtn=ObservableField<Drawable>()

    fun onClickAddLoanEnquiry() {
        iLoanInfoListener.fabAddLoanEnquiry()
    }
}
