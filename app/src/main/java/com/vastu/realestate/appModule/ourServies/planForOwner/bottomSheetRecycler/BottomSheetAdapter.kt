package com.vastu.realestate.appModule.ourServies.planForOwner.bottomSheetRecycler

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.databinding.OurServiceItemBinding


class BottomSheetAdapter(private val dataList: List<PlanDuration>)
    : RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {
     lateinit var ourServiceItemBinding: OurServiceItemBinding
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        ourServiceItemBinding = OurServiceItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(ourServiceItemBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)

        ourServiceItemBinding.radioButton.isChecked = data.selectedRadio == true
        ourServiceItemBinding.txtMonth.text = data.months
        ourServiceItemBinding.txtGstAmt.text = data.amtIncGst
        ourServiceItemBinding.txtAmt.text = data.amtMonthly
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class ViewHolder(val binding: OurServiceItemBinding)
        :RecyclerView.ViewHolder(binding.root) {
        fun bind(planDuration: PlanDuration) {
            binding.planDuration = planDuration
        }
    }
}
