package com.example.foodorderingadminapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingadminapplication.R
import com.example.foodorderingadminapplication.databinding.DeliveryItemBinding
import java.util.ArrayList


class DeliveryAdapter(private val customerNames: MutableList<String>, private val moneyStatus: MutableList<Boolean>): RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }



    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = customerNames.size
    inner class DeliveryViewHolder(private val bingding: DeliveryItemBinding): RecyclerView.ViewHolder(bingding.root) {
        fun bind(position: Int) {
            bingding.apply {
                customerName.text= customerNames[position]
                if(moneyStatus[position]){
                    customerStatus.text = "Received"
                } else{
                    customerStatus.text = "NotReceived"
                }
                val colorMap = mapOf(
                    true to Color.parseColor("#4BFF93"), false to Color.parseColor("#FF4B4B")
                )
                customerStatus.setTextColor(colorMap[moneyStatus[position]]?: Color.BLACK)
                when (moneyStatus[position]) {
                    true -> {imageView.setImageResource(R.drawable.baseline_file_download_done_24)}
                    false -> imageView.setImageResource(R.drawable.baseline_airport_shuttle_24)
                }
            }
        }

    }
}