package com.vastu.realestate.appModule.contactus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.employee.viewModel.EmployeeDetailsViewModel
import com.vastu.realestate.databinding.FragmentContactUsBinding


class ContactUsFragment : BaseFragment(), IToolbarListener {

    lateinit var contactUsViewModel: ContactUsViewModel
    lateinit var drawerViewModel: DrawerViewModel
    lateinit var contactUsBinding: FragmentContactUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactUsViewModel = ViewModelProvider(this)[ContactUsViewModel::class.java]
        drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
        contactUsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_us,container,false)
        contactUsBinding.contactUsViewModel = contactUsViewModel
        drawerViewModel.iToolbarListener = this
        contactUsBinding.drawerViewModel= drawerViewModel
        initView()
        return contactUsBinding.root
    }

    private fun initView() {
        drawerViewModel.toolbarTitle.set(getString(R.string.contact_us))
        drawerViewModel.isDashBoard.set(false)
    }

    override fun onClickBack() {
        findNavController().navigate(R.id.action_ContactUs_to_Dashboard)
    }

    override fun onClickMenu() {

    }

    override fun onClickNotification() {

    }
    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }*/


}