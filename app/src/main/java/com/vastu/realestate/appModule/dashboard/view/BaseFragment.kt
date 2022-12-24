package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vastu.realestate.customProgressDialog.CustomProgressDialog

open class BaseFragment : Fragment() {

    private fun getVastuActivity(): BaseActivity? {
        return super.getActivity() as BaseActivity?
    }
    fun showProgressDialog(){
        getVastuActivity()?.showProgressDialog()
    }
     fun hideProgressDialog(){
        getVastuActivity()?.hideProgressDialog()
    }

}