package com.example.foodorderingadminapplication.adapter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingadminapplication.R
import com.example.foodorderingadminapplication.databinding.DeliveryItemBinding
import java.util.ArrayList

class DeliveryAdapter(private val customerNames: ArrayList<String>, private val moneyStatus: ArrayList<String>): RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


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
                customerStatus.text= moneyStatus[position]
                val colorMap = mapOf(
                    "Received" to Color.parseColor("#4BFF93"), "Not Received" to Color.parseColor("#FF4B4B"), "Pending" to Color.parseColor("#a3a3a7")
                )
                customerStatus.setTextColor(colorMap[moneyStatus[position]]?: Color.BLACK)
                when (moneyStatus[position]) {
                    "Received" -> imageView.setImageResource(R.drawable.baseline_file_download_done_24)
                    "Not Received" -> imageView.setImageResource(R.drawable.baseline_airport_shuttle_24)
                    "Pending" -> imageView.setImageResource(R.drawable.baseline_pending_actions_24)
                }
            }
        }

    }
}