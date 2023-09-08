package com.vastu.realestate.appModule.feedback

import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.vastu.realestate.R

class FeedbackViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var mContext: Application
    var selectedQuery: ObservableField<String> = ObservableField<String>()
    init {
        mContext=application
        selectedQuery.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.e("***", "text :${selectedQuery.get()}")
            }
        })
    }
    val allQuery: ObservableField<List<String>> =
        ObservableField<List<String>>(listOf(mContext.getString(R.string.select),mContext.getString(R.string.app_crash), mContext.getString(R.string.search_issue)
            , mContext.getString(R.string.asking_question),mContext.getString(R.string.improvement)
            ,mContext.getString(R.string.other)))

}