package com.vastu.realestate.appModule.enquirylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vastu.enquiry.loan.model.response.LoanData
import com.vastu.realestate.databinding.LoanEnquiryItemviewBinding

class LoanEnquiryAdapter(private val loanDataList :List<LoanData>):
    RecyclerView.Adapter<LoanEnquiryViewHolder>() {

    private lateinit var binding: LoanEnquiryItemviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanEnquiryViewHolder {
       binding = LoanEnquiryItemviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return LoanEnquiryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanEnquiryViewHolder, position: Int) {
        val loan = loanDataList[position]
        holder.bind(loan)
        binding.loanNameTextview.text = loan.firstName+" "+loan.middleName+" "+loan.lastName

    }

    override fun getItemCount(): Int = loanDataList.size
    }

    class LoanEnquiryViewHolder(val binding: LoanEnquiryItemviewBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(loanData: LoanData){
               binding.loanData = loanData
            }
    }

