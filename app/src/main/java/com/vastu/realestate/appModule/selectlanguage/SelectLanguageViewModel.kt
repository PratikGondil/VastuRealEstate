package com.vastu.realestate.appModule.selectlanguage

import android.app.Application
import android.app.backup.SharedPreferencesBackupHelper
import android.content.SharedPreferences
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.R

class SelectLanguageViewModel(application: Application): AndroidViewModel(application) {
    var mContext :Application
    var isMarathiSelected = ObservableField<Boolean>(false)
    var isEnglishSelected = ObservableField<Boolean>(false)
    init {
        mContext = application
    }
    var btnBackground = ObservableField(mContext.getDrawable(R.drawable.button_inactive_background))


    fun onNextClick()
    {
        var isMarathiSelected = isMarathiSelected.get()
        if(isMarathiSelected == true)
        {

        }

    }

}