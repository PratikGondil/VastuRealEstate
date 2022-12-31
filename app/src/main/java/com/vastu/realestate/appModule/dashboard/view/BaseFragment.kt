package com.vastu.realestate.appModule.dashboard.view

import androidx.fragment.app.Fragment

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
    fun showDialog(message:String,isSuccess:Boolean){
        getVastuActivity()?.showDialog(message,isSuccess)
    }

}