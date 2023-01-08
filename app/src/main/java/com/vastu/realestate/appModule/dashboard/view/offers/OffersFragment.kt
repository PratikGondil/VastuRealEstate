package com.vastu.realestate.appModule.dashboard.view.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.offer.OfferViewModel
import com.vastu.realestate.databinding.OffersFragmentBinding

class OffersFragment:Fragment(), IToolbarListener {
    lateinit var offerViewModel: OfferViewModel
    lateinit var offersFragmentBinding: OffersFragmentBinding
    private lateinit var drawerViewModel: DrawerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        offerViewModel = ViewModelProvider(this)[OfferViewModel::class.java]
        drawerViewModel =ViewModelProvider(this)[DrawerViewModel::class.java]
        offersFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.offers_fragment,container,false)
        offersFragmentBinding.offerViewModel = offerViewModel
        offersFragmentBinding.drawerViewModel = drawerViewModel
        drawerViewModel.iToolbarListener = this
        offersFragmentBinding.lifecycleOwner = this
        return offersFragmentBinding.root
    }
    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.offers))
        drawerViewModel.isDashBoard.set(false)
    }

    override fun onClickBack() {
        activity?.onBackPressed()
    }

    override fun onClickMenu() {
        //to be..
    }

    override fun onClickNotification() {
        //to be..
    }
}