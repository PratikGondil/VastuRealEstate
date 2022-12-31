package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.appModule.dashboard.uiInterfaces.ILoanListener
import com.vastu.realestate.appModule.login.uiInterfaces.ILoginViewListener

class LoanViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var iLoanListener: ILoanListener

    fun onClickPersonalLoan() {
        iLoanListener.onClickPersonalLoan()
    }

    fun onClickHomeHomeLoan() {
        iLoanListener.onClickHomeLoan()
    }

    fun onClickCarLoan() {
        iLoanListener.onClickCarLoan()
    }

    fun onClickAddLoanEnquiry() {
        iLoanListener.fabAddLoanEnquiry()
    }
}
