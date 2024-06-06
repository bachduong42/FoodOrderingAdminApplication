package com.example.foodorderingadminapplication.adapter


import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingadminapplication.databinding.ItemItemBinding
import com.example.foodorderingadminapplication.model.AllMenu
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(private var item: ArrayList<AllMenu>,
    private val context: Context,
    private var databaseReference: DatabaseReference,
                      private val nameOfRestaurant: String)
    : RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {
    private val itemQuantities = IntArray(item.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding=ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return AddItemViewHolder(binding)
    }
    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = item.size
    inner class AddItemViewHolder(private val binding: ItemItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = item[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)
                foodName.text= menuItem.foodName
                foodPrice.text = menuItem.foodPrice
                foodRestaurant.text = nameOfRestaurant
                Glide.with(context).load(uri).into(foodImage)
                tvNumber.text = quantity.toString()
                btnReduce.setOnClickListener(){
                    decreaseQuantities(position)
                }
                btnIncrease.setOnClickListener(){
                    increaseQuantities(position)
                }
                btnDelete.setOnClickListener(){
                    deleteQuantities(position)
                }
            }
        }

        private fun increaseQuantities(position: Int) {
            if(itemQuantities[position]<10){
                itemQuantities[position]++
                binding.tvNumber.text= itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantities(position: Int) {
            if(itemQuantities[position]>1){
                itemQuantities[position]--
                binding.tvNumber.text= itemQuantities[position].toString()
            }
        }
        private fun deleteQuantities(position: Int) {
            item.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,item.size)
        }
    }

}