package com.example.foodorderingadminapplication.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingadminapplication.databinding.OrderDetailsItemsBinding

class OrderDetailsAdapter(private val context: Context,
    private var foodNames: MutableList<String>,
    private var foodImages: MutableList<String>,
    private var foodQuantities: MutableList<String>,
    private var foodPrices: MutableList<String> ): RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>(){
    class OrderDetailsViewHolder(binding: OrderDetailsItemsBinding): RecyclerView.ViewHolder(binding.root) {
        var foodName = binding.tvNameFood
        var image = binding.imgFood
        var quantity = binding.tvCount
        var price = binding.tvPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        return OrderDetailsViewHolder(OrderDetailsItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return foodNames.size
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        holder.foodName.text = foodNames[position]
        holder.price.text = foodPrices[position]
        holder.quantity.text = foodQuantities[position]
        val image = foodImages[position]
        val uri = Uri.parse(image)
        Glide.with(context).load(uri).into(holder.image)
    }
}