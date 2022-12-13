package com.vastu.realestate.appModule.login.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.appModule.login.viewModel.LoginViewModel
import com.vastu.realestate.R
import com.vastu.realestate.appModule.login.uiInterfaces.ILoginViewListener
import com.vastu.realestate.commoncore.model.otp.ObjUserData
import com.vastu.realestate.customProgressDialog.CustomProgressDialog
import com.vastu.realestate.databinding.LoginFragmentBinding
import com.vastu.realestate.logincore.model.response.ObjLoginResponse
import com.vastu.realestate.logincore.model.response.ObjLoginResponseMain
import com.vastu.realestate.registrationcore.model.response.ObjRegisterDlts
import com.vastu.realestate.utils.BaseConstant

class LoginFragment : Fragment(), ILoginViewListener {

    lateinit var viewModel: LoginViewModel
    lateinit var binder: LoginFragmentBinding
     var objUserData = ObjUserData()
    lateinit var customProgressDialog :CustomProgressDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binder = DataBindingUtil.inflate(inflater,R.layout.login_fragment,container,false)
        binder.lifecycleOwner = this
        binder.loginViewModel = viewModel
        viewModel.iLoginViewListener = this
        customProgressDialog = CustomProgressDialog.getInstance()
//        initView()
        return binder.root
    }


    override fun onSendOtpClick() {
//        val bundle = Bundle()
//        objRegisterDlts = objRegisterDlts.copy(userId = 1)
//        bundle.putSerializable(BaseConstant.REGISTER_DTLS_OBJ, objRegisterDlts)
//        findNavController().navigate(R.id.action_LoginSignUpFragment_To_OTPFragment,bundle)
        if(viewModel.isValidMobileNumber.get()!!){
            customProgressDialog.show(requireContext())
            viewModel.callLoginApi(viewModel.mobileNumber.get().toString())
        }
        else{
//            viewModel.errorVisible.set(View.VISIBLE)
            binder.tilMobileNumLayout.helperText= viewModel.error.get()
        }

//        if(!viewModel.mobileNumber.get().isNullOrEmpty()){
//            setOtpView()
//        }
    }
    override fun launchOtpScreen(objLoginResponseMain: ObjLoginResponseMain) {
        val bundle = Bundle()
        objUserData = objUserData.copy(userID = objLoginResponseMain.objLoginDtls.userId,mobile = viewModel.mobileNumber.get())
        bundle.putSerializable(BaseConstant.REGISTER_DTLS_OBJ, objUserData)
        findNavController().navigate(R.id.action_LoginSignUpFragment_To_OTPFragment,bundle)
    }

    override fun onLoginFail(objLoginResponse: ObjLoginResponse) {
        Toast.makeText(requireContext(),objLoginResponse.objResponseStatusHdr.statusDescr,
            Toast.LENGTH_LONG).show()

    }
}