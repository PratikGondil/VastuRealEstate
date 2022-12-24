package com.vastu.realestate.appModule.application

import android.app.Application
import com.vastu.realestate.utils.PreferenceManger

class VastuApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceManger.with(this)
    }
}