package com.example.foodorderingadminapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingadminapplication.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding: ActivityOutForDeliveryBinding by lazy{
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}