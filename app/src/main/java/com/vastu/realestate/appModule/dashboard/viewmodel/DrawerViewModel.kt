package com.vastu.realestate.appModule.dashboard.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.INavDrawerListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener

class DrawerViewModel(application: Application) : AndroidViewModel(application) {
    var mContext :Application
    init {
        mContext = application
    }

    var userName = ObservableField("")
    var mobileNo = ObservableField("")

    lateinit var iNavDrawerListener: INavDrawerListener
    lateinit var iToolbarListener: IToolbarListener

    var isDashBoard = ObservableField<Boolean>(true)
    var toolbarTitle = ObservableField(mContext.getString(R.string.vastu))

    fun onCloseClick(){
        iNavDrawerListener.onClickClose()
    }
    fun onBackClick(){
        iToolbarListener.onClickBack()
    }
    fun onMenuClick(){
        iToolbarListener.onClickMenu()
    }
    fun onNotificationClick(){
        iToolbarListener.onClickNotification()
    }

    fun onEnquiryClick(){
        iNavDrawerListener.onClickEnquiry()
    }
    fun onAddPropertyClick(){
        iNavDrawerListener.onClickAddProperty()
    }
    fun onContactUsClick(){
        iNavDrawerListener.onClickContactUs()
    }
    fun onSettingsClick(){
        iNavDrawerListener.onClickSettings()
    }

    fun onLogoutClick(){
        iNavDrawerListener.onClickLogout()
    }
}