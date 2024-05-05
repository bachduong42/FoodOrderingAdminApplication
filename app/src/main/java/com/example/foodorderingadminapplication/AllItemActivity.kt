package com.example.foodorderingadminapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingadminapplication.adapter.AddItemAdapter
import com.example.foodorderingadminapplication.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {
    private val binding: ActivityAllItemBinding by lazy{
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val menuFoodName = listOf("Burger", "Pizza", "Sandwich","Burger", "Pizza", "Sandwich")
        val menuFoodRestaurant = listOf("Con Toc", "Mina Restaurant", "Hiign Min","Valeing", "Kim Quy Restaurant", "BBQ")
        val menuFoodPrice = listOf("$ 30", "$ 50", "$ 60","$ 70", "$ 30", "$ 80")
        val menuFoodImage = listOf(
            R.drawable.pizza,R.drawable.pizza,R.drawable.pizza,
            R.drawable.pizza,R.drawable.pizza,R.drawable.pizza
        )
        val adapter = AddItemAdapter(ArrayList(menuFoodName), ArrayList(menuFoodRestaurant),
            ArrayList(menuFoodPrice),ArrayList(menuFoodImage))
        binding.rvItems.layoutManager= LinearLayoutManager(this)
        binding.rvItems.adapter= adapter

        binding.btnBack.setOnClickListener{
            finish();
        }
    }
}