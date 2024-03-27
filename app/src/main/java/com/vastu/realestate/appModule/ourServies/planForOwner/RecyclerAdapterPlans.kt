package com.vastu.realestate.appModule.ourServies.planForOwner


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.R

class RecyclerAdapterPlans(val context: Context, val dataList: List<com.vastu.realestate.appModule.ourServies.planForOwner.response.PlanData>, var iClickPlanCheck: IClickPlanCheck) :
    RecyclerView.Adapter<RecyclerAdapterPlans.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //item initalization
        var txtFirstPlanText: TextView = itemView.findViewById(R.id.txtFirstPlanText)
        var txtFirstPlanPrice: TextView = itemView.findViewById(R.id.txtFirstPlanPrice)
        var planCheckBox: CheckBox = itemView.findViewById(R.id.txtFirstPlanCheck)



    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapterPlans.MyViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_plans, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterPlans.MyViewHolder, position: Int) {
        holder.txtFirstPlanText.setText(dataList.get(position).plan_name)
        holder.txtFirstPlanPrice.setText("₹ "+dataList.get(position).price)

        holder.planCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
          iClickPlanCheck.selectedPlan(position.toString())
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}