package com.vastu.realestate.appModule.login.view.fragment

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.appModule.login.viewModel.LoginViewModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.login.uiInterfaces.ILoginViewListener
import com.vastu.realestate.commoncore.model.otp.ObjUserData
import com.vastu.realestate.customProgressDialog.CustomProgressDialog
import com.vastu.realestate.databinding.LoginFragmentBinding
import com.vastu.realestate.logincore.model.response.ObjLoginResponse
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain
import com.vastu.realestate.utils.BaseConstant

class LoginFragment : BaseFragment(), ILoginViewListener {

    lateinit var viewModel: LoginViewModel
    lateinit var binder: LoginFragmentBinding
     var objUserData = ObjUserData()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binder = DataBindingUtil.inflate(inflater,R.layout.login_fragment,container,false)
        binder.lifecycleOwner = this
        binder.loginViewModel = viewModel
        viewModel.iLoginViewListener = this
       //initView()
        return binder.root
    }


    override fun onSendOtpClick() {
        createDialog(this)


    }

    fun redirectedToAPIAfterTerms()
    {
        if(viewModel.isValidMobileNumber.get()!!){
            showProgressDialog()
            viewModel.callLoginApi(viewModel.mobileNumber.get().toString())
        }
        else{
            binder.tilMobileNumLayout.helperText= viewModel.error.get()
        }
    }


    override fun launchOtpScreen(objLoginResponseMain: ObjLoginResponseMain) {
        hideProgressDialog()
        val bundle = Bundle()
        objUserData = objUserData.copy(userID = objLoginResponseMain.objLoginDtls.userId,mobile = viewModel.mobileNumber.get())
        bundle.putSerializable(BaseConstant.REGISTER_DTLS_OBJ, objUserData)
        findNavController().navigate(R.id.action_LoginSignUpFragment_To_OTPFragment,bundle)
    }

    override fun onLoginFail(objLoginResponse: ObjLoginResponse) {
        hideProgressDialog()
        showDialog(objLoginResponse.objResponseStatusHdr.statusDescr,false,false)
    }

    override fun onUserNotConnected() {
        hideProgressDialog()
        showDialog("",false,true)
    }
}