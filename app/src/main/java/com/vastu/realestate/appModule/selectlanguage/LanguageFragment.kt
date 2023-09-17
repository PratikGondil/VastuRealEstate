package com.vastu.realestate.appModule.selectlanguage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.selectlanguage.uiinterface.iSelectLanguage
import com.vastu.realestate.databinding.LanguageFragmentBinding


class LanguageFragment : BaseFragment(), iSelectLanguage ,IToolbarListener{

        lateinit var selectLanguageViewModel: SelectLanguageViewModel
        lateinit var selectLanguageBinding: LanguageFragmentBinding
        lateinit var drawerViewModel: DrawerViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            selectLanguageViewModel = ViewModelProvider(this)[SelectLanguageViewModel::class.java]
            drawerViewModel = ViewModelProvider(this)[DrawerViewModel::class.java]
            selectLanguageBinding = DataBindingUtil.inflate(inflater, R.layout.language_fragment,container,false)
            selectLanguageBinding.selectLanguageViewModel = selectLanguageViewModel
            selectLanguageViewModel.iSelectLanguage = this
            drawerViewModel.iToolbarListener = this
            selectLanguageBinding.drawerViewModel= drawerViewModel
            initView()
            return selectLanguageBinding.root
        }

        private fun initView() {
            drawerViewModel.toolbarTitle.set(getString(R.string.change_language))
            drawerViewModel.isDashBoard.set(false)
            setOnCheckedChangeListener()

        }

        private fun setOnCheckedChangeListener() {
            selectLanguageBinding.radioLanguage.setOnCheckedChangeListener { group, checkedId ->
                selectLanguageViewModel.btnBackground.set(ContextCompat.getDrawable(requireContext(),R.drawable.round_button_background))

            }
        }

        override fun redirectToLoginSignUpPage(selectedLanguageCode: String) {
            setAppLocale(selectedLanguageCode)
            findNavController().navigate(R.id.action_languageFragment_to_dashboardFragment)

        }

    override fun onClickBack() {
        findNavController().navigate(R.id.action_languageFragment_to_dashboardFragment)

    }

    override fun onClickMenu() {
        TODO("Not yet implemented")
    }

    override fun onClickNotification() {
        TODO("Not yet implemented")
    }
}
