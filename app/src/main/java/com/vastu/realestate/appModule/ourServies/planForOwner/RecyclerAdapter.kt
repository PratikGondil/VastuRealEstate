package com.vastu.realestate.appModule.ourServies.planForOwner


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vastu.realestate.R

class RecyclerAdapter(val context: Context, val dataList: List<String>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //item initalization
        var txtPlanDescription: TextView = itemView.findViewById(R.id.txtPlanDescription)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.MyViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.plan_description, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MyViewHolder, position: Int) {
        holder.txtPlanDescription.setText(dataList.get(position))
        holder.itemView.setOnClickListener {

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