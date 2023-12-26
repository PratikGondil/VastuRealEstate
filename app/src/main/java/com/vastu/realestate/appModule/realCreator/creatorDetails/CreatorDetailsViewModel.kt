package com.vastu.realestate.appModule.realCreator.creatorDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class CreatorDetailsViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var iCreatorDetailsListener: ICreatorDetailsListener
}