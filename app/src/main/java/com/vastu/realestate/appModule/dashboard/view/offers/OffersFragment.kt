package com.vastu.realestate.appModule.dashboard.view.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vastu.offers.model.response.OfferData
import com.vastu.offers.model.response.OffersMainResponse
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.OffersAdapter
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IOffersListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IToolbarListener
import com.vastu.realestate.appModule.dashboard.view.BaseFragment
import com.vastu.realestate.appModule.dashboard.viewmodel.DrawerViewModel
import com.vastu.realestate.appModule.dashboard.viewmodel.offer.OfferViewModel
import com.vastu.realestate.databinding.OffersFragmentBinding
import com.vastu.realestate.utils.BaseConstant
import com.vastu.realestate.utils.IRecycleViewClick

class OffersFragment:BaseFragment(), IToolbarListener,IOffersListener,IRecycleViewClick {
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
        offerViewModel.iOffersListener = this
        getOffers()
        return offersFragmentBinding.root
    }
    override fun onResume() {
        super.onResume()
        drawerViewModel.toolbarTitle.set(getString(R.string.offers))
        drawerViewModel.isDashBoard.set(false)
    }
    private fun getOffers(){
        showProgressDialog()
        offerViewModel.callGetOffers()
    }

    override fun onSuccessGetOffers(offersMainResponse: OffersMainResponse) {
        hideProgressDialog()
        val offersList = offersMainResponse.getofferDetailsResponse.offerData
        getOffersDetails(offersList)

    }
    private fun getOffersDetails(offerData:List<OfferData>) {
        try {
            val recyclerViewOffers = offersFragmentBinding.rvOffers
            val offersAdapter = OffersAdapter(offerData,this)
            recyclerViewOffers.adapter = offersAdapter
            recyclerViewOffers.layoutManager = LinearLayoutManager(activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFailureGetOffers(offersMainResponse: OffersMainResponse) {
        hideProgressDialog()
        showDialog(offersMainResponse.offerResponse.responseStatusHeader.statusDescription!!,isSuccess = false,isNetworkFailure = false)
    }

    override fun onUserNotConnected() {
        showDialog("",isSuccess = false,isNetworkFailure = true)
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

    override fun onClick(propertyId: String) {
        if(isInteger(propertyId))
        {
            val bundle = Bundle()
            bundle.putSerializable(BaseConstant.PROPERTY_ID,propertyId)
            findNavController().navigate(R.id.action_OffersFragment_to_RealEstateDetailsFragment,bundle)
        }

    }

    fun isInteger(str: String?) = str?.toIntOrNull()?.let { true } ?: false


}