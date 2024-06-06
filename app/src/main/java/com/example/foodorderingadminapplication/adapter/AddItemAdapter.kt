package com.example.foodorderingadminapplication.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingadminapplication.databinding.ItemItemBinding

class AddItemAdapter(private val MenuItemName: ArrayList<String>, private val MenuItemRestaurant: ArrayList<String>, private val MenuItemPrice: ArrayList<String>, private val MenuItemImage: ArrayList<Int>): RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {
    private val itemQuantities = IntArray(MenuItemName.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding=ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return AddItemViewHolder(binding)
    }
    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }
    override fun getItemCount(): Int = MenuItemName.size
    inner class AddItemViewHolder(private val binding: ItemItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply {
                val quantity = itemQuantities[position]
                foodName.text= MenuItemName[position]
                foodPrice.text = MenuItemPrice[position]
                foodRestaurant.text = MenuItemRestaurant[position]
                foodImage.setImageResource((MenuItemImage[position]))
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
            MenuItemName.removeAt(position)
            MenuItemPrice.removeAt(position)
            MenuItemRestaurant.removeAt(position)
            MenuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,MenuItemName.size)
        }
    }

}