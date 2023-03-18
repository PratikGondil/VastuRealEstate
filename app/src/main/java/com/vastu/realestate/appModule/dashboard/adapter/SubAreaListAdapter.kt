package com.vastu.realestate.appModule.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterViewHandler
import com.vastu.realestate.databinding.SubAreaListAdapterBinding
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData
import com.vastu.realestate.utils.PreferenceKEYS
import com.vastu.realestate.utils.PreferenceManger
import com.vastu.realestatecore.model.request.ObjFilterData

class SubAreaListAdapter(
   var talukaDataList: ArrayList<ObjCityAreaData>?,
    var iFilterViewHandler: IFilterViewHandler
): RecyclerView.Adapter<SubAreaListAdapter.SubAreaListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubAreaListViewHolder {
        val binding: SubAreaListAdapterBinding = SubAreaListAdapterBinding.inflate(LayoutInflater.from(parent.context))

        return SubAreaListViewHolder( binding,binding.root)
    }

    override fun onBindViewHolder(holder: SubAreaListViewHolder, position: Int) {
        val currentArea = talukaDataList?.get(position)
        if(PreferenceManger.get<ObjFilterData>(PreferenceKEYS.FILTERDATA)!= null) {
            val objFilterData = PreferenceManger.get<ObjFilterData>(PreferenceKEYS.FILTERDATA)!!
            if (objFilterData != null) {
                for (i in 0 until objFilterData.subAreaId.size) {
                    if (objFilterData.subAreaId[i].equals(currentArea!!.areaId))
                        holder.binding.checkboxCity.isChecked = true
                }
            }
        }
        if (currentArea != null) {
            holder.bind(currentArea)
        }
        holder.binding.checkboxCity.setOnClickListener {

            if (holder.binding.checkboxCity.isChecked) {
               iFilterViewHandler.onSubAreaClickListener(currentArea!!,true)
            }
            else{
                iFilterViewHandler.onSubAreaClickListener(currentArea!!,false)

            }
        }
    }

    override fun getItemCount(): Int {
        return talukaDataList!!.size
    }
    class SubAreaListViewHolder(@Nullable val binding: SubAreaListAdapterBinding, view: View):RecyclerView.ViewHolder(view){
        fun bind(item: ObjCityAreaData) {

            binding.cityList = item
            binding.executePendingBindings()
        }
    }
}