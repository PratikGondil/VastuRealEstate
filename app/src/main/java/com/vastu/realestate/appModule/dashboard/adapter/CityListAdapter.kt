package com.vastu.realestate.appModule.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterViewHandler
import com.vastu.realestate.databinding.SubAreaListAdapterBinding
import com.vastu.realestate.registrationcore.model.response.cityList.ObjTalukaDataList
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData

class CityListAdapter(
    var talukaDataList: ArrayList<ObjTalukaDataList>?,
    var iFilterViewHandler: IFilterViewHandler
): RecyclerView.Adapter<CityListAdapter.CityListViewHolder>()  {

    class CityListViewHolder(@Nullable val binding: SubAreaListAdapterBinding, view: View)
        :RecyclerView.ViewHolder(view){
        fun bind(item: ObjCityAreaData) {

            binding.cityList = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListViewHolder {
        val binding: SubAreaListAdapterBinding = SubAreaListAdapterBinding.inflate(LayoutInflater.from(parent.context))

        return CityListViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: CityListViewHolder, position: Int) {
        val currentArea = talukaDataList?.get(position)
        if (currentArea != null) {
//            holder.bind(currentArea)
        }
    }

    override fun getItemCount(): Int {
        return talukaDataList!!.size
    }
}