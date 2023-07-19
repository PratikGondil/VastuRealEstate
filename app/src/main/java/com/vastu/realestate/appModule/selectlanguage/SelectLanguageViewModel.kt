package com.vastu.realestate.appModule.selectlanguage

import android.app.Application
import android.app.backup.SharedPreferencesBackupHelper
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.networkService.util.Constants
import com.vastu.realestate.R
import com.vastu.realestate.utils.PreferenceManger

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
        if(isMarathiSelected.get() == true)
        {
            PreferenceManger.putString<String>(Constants.SELECTED_LANGUAGE,Constants.MARATHI)

        }else if(isEnglishSelected.get() == true){
            PreferenceManger.putString<String>(Constants.SELECTED_LANGUAGE,Constants.ENGLISH)
        }

    }

}