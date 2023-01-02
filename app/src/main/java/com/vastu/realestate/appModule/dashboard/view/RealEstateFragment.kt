package com.vastu.realestate.appModule.dashboard.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vastu.realestate.R
import com.vastu.realestate.appModule.dashboard.adapter.RealEstateAdapter
import com.vastu.realestate.appModule.dashboard.model.RealEstateList
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IBackClickListener
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterClickListener
import com.vastu.realestate.appModule.dashboard.view.filter.SortAndFilterScreen
import com.vastu.realestate.appModule.dashboard.viewmodel.RealEstateViewModel
import com.vastu.realestate.databinding.FragmentRealEstateBinding


class RealEstateFragment : Fragment(), IBackClickListener, IFilterClickListener {
    private lateinit var realEstateBinding: FragmentRealEstateBinding
    private lateinit var realEstateViewModel: RealEstateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realEstateViewModel = ViewModelProvider(this)[RealEstateViewModel::class.java]
        realEstateBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_real_estate, container, false)
        realEstateBinding.lifecycleOwner = this
        realEstateBinding.realEstateViewModel = realEstateViewModel
        realEstateViewModel.iBackClickListener = this
        realEstateViewModel.iFilterClickListener = this
        return realEstateBinding.root;
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(getString(R.string.real_estate))
        getRealEstateDetails()
    }

    private fun getRealEstateDetails() {
        val recyclerViewRealEstate = realEstateBinding.rvRealEstste
        val realEstates = RealEstateList.getRealEstateData(requireContext())
        val realEstateAdapter = RealEstateAdapter(realEstates)

        recyclerViewRealEstate.adapter = realEstateAdapter
        recyclerViewRealEstate.layoutManager = LinearLayoutManager(activity)
        recyclerViewRealEstate.setHasFixedSize(true)

        recyclerViewRealEstate.addItemDecoration(
            DividerItemDecoration(activity,
                LinearLayoutManager.VERTICAL
            )
        )
        recyclerViewRealEstate.setOnClickListener {
            findNavController().navigate(R.id.action_RealEstateFragment_to_RealEstateDetailsFragment)
        }
    }

    override fun onBackClick() {
        findNavController().navigate(R.id.action_RealEstateFragment_to_VastuDashboardFragment)
    }

    override fun setFilterView() {
        try{
            BottomSheetBehavior.from(realEstateBinding.filterFragment).apply {
                this.state = BottomSheetBehavior.STATE_EXPANDED
            }
//           manageVisibiltyOfChildView(View.GONE, View.VISIBLE)

//            var bundle = Bundle()
//            bundle.putSerializable(BaseConstants.COUNTRY_LIST, objCountryListResponseData)
//            bundle.putParcelable(BaseConstants.ADD_NEW_RECIPIENT, this)
//            bundle.putString(BaseConstants.Flow, BaseConstants.ADD_NEW_RECIPIENT)
//            BaseUtils.replaceCurrentFramentWithBundle(
//                (activity as AppCompatActivity?)!!,
//                CountryListWithFlagFragment(), bundle
//            )
//            val transaction = requireActivity().supportFragmentManager.beginTransaction()
//            transaction.setCustomAnimations(R.anim.slide_push_up_in, R.anim.noanimation)
//            transaction.replace(R.id.fragment_container, SortAndFilterScreen())
//            transaction.addToBackStack(null)
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            transaction.commit()
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

 fun manageVisibiltyOfChildView(isRealEstateVisible:Int,isFilterViewVisible:Int){
     realEstateViewModel.isFilterViewVisible.set(isFilterViewVisible)
     realEstateViewModel.isRealEstateVisible.set(isRealEstateVisible)
 }
}