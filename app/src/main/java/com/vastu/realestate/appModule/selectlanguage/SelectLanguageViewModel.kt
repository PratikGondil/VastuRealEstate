package com.vastu.realestate.appModule.selectlanguage

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.networkService.util.Constants
import com.vastu.realestate.R
import com.vastu.realestate.appModule.selectlanguage.uiinterface.iSelectLanguage
import com.vastu.realestate.utils.PreferenceManger

class SelectLanguageViewModel(application: Application): AndroidViewModel(application) {
    var mContext :Application
    var isMarathiSelected = ObservableField<Boolean>(false)
    var isEnglishSelected = ObservableField<Boolean>(false)
    var loginView=ObservableField(View.VISIBLE)

    lateinit var iSelectLanguage:iSelectLanguage
    init {
        mContext = application
    }
    var btnBackground = ObservableField(mContext.getDrawable(R.drawable.button_inactive_background))
    var btnText=ObservableField(mContext.resources.getString(R.string.next))

    fun onNextClick()
    {
        var selectedLanguageCode =""

        if(isMarathiSelected.get() == true)
        {
            selectedLanguageCode ="mr"
            PreferenceManger.putString<String>(Constants.SELECTED_LANGUAGE,Constants.MARATHI)

        }else if(isEnglishSelected.get() == true){
            selectedLanguageCode ="en"
            PreferenceManger.putString<String>(Constants.SELECTED_LANGUAGE,Constants.ENGLISH)
        }
        iSelectLanguage.redirectToLoginSignUpPage(selectedLanguageCode)

    }

}