package com.example.foodorderingadminapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingadminapplication.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy{
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            finish();
        }
    }
}