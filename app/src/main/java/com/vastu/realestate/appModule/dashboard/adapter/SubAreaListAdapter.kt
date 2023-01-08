package com.vastu.realestate.appModule.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.appModule.dashboard.uiInterfaces.IFilterViewHandler
import com.vastu.realestate.databinding.SubAreaListAdapterBinding
import com.vastu.realestate.registrationcore.model.response.subArea.ObjCityAreaData

class SubAreaListAdapter(
   var talukaDataList: ArrayList<ObjCityAreaData>?,
    var iFilterViewHandler: IFilterViewHandler
): RecyclerView.Adapter<SubAreaListAdapter.SubAreaListViewHolder>() {
    private var previousHolder: SubAreaListViewHolder? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubAreaListViewHolder {
        val binding: SubAreaListAdapterBinding = SubAreaListAdapterBinding.inflate(LayoutInflater.from(parent.context))

        return SubAreaListViewHolder( binding,binding.root)
    }

    override fun onBindViewHolder(holder: SubAreaListViewHolder, position: Int) {
        val currentArea = talukaDataList?.get(position)
        previousHolder = holder

        if (currentArea != null) {
            holder.bind(currentArea)
        }
        holder.binding.checkboxCity.setOnClickListener {
            if (previousHolder != null) {
                previousHolder = null
            }
            previousHolder = holder
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