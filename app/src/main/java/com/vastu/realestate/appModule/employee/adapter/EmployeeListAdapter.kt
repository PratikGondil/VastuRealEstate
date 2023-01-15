package com.vastu.realestate.appModule.employee.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.vastu.enquiry.employee.model.response.ObjEmployeeData
import com.vastu.realestate.appModule.employee.uiListener.IEmpListViewListener
import com.vastu.realestate.databinding.EmployeeListShimmerBinding

class EmployeeListAdapter(var empList:ArrayList<ObjEmployeeData>,var iEmpListViewListener: IEmpListViewListener): RecyclerView.Adapter<EmployeeListAdapter.EmployeeListViewHolder>() {
    var previousHolder :EmployeeListViewHolder? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeListViewHolder {
        val binding: EmployeeListShimmerBinding = EmployeeListShimmerBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return EmployeeListViewHolder(binding,binding.root)
    }

    override fun onBindViewHolder(
        holder: EmployeeListViewHolder,
        position: Int
    ) {
        val currentEmpData = empList.get(position)
        previousHolder = holder

        if (currentEmpData != null) {
            holder.bind(currentEmpData)
        }
        holder.empListShimmerBinding.cvEmployee.setOnClickListener {
            if (previousHolder != null) {
                previousHolder = null
            }
            previousHolder = holder
            iEmpListViewListener.onEmpSelected(currentEmpData)
        }
    }

    override fun getItemCount(): Int {
        return empList.size
    }
    class EmployeeListViewHolder(@Nullable var empListShimmerBinding: EmployeeListShimmerBinding, view: View):RecyclerView.ViewHolder(view){
        fun bind(item: ObjEmployeeData) {

            empListShimmerBinding.employeeList = item
            empListShimmerBinding.executePendingBindings()
        }
    }
}