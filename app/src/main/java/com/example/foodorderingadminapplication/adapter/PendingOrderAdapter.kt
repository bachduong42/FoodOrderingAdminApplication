package com.example.foodorderingadminapplication.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingadminapplication.databinding.PendingOrdersItemBinding


class PendingOrderAdapter(
    private val customerNames: MutableList<String>,
    private val quantities: MutableList<String>,
    private val imageFoods: MutableList<String>,
    private val context: Context,
    private val itemClicked: OnItemClicked
): RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    interface OnItemClicked {
        fun onItemClickListener(position: Int)
        fun onItemAcceptClickListener(position: Int)
        fun onItemDispatchClickListener(position: Int)
    }

    class PendingOrderViewHolder(private var binding: PendingOrdersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvNameFood
        var imageFood = binding.imgFood
        var quantity = binding.tvCount
        var btnAccept = binding.btnAccept
        fun bind(position: Int, itemClicked: OnItemClicked) {
            itemView.setOnClickListener {
                itemClicked.onItemClickListener(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        return PendingOrderViewHolder(
            PendingOrdersItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return customerNames.size
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position, itemClicked)

    }
}