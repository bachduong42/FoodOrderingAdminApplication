package com.example.foodorderingadminapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingadminapplication.databinding.ActivityAdminMainBinding

class AdminMainActivity : AppCompatActivity() {
    private val binding: ActivityAdminMainBinding by lazy{
        ActivityAdminMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnAddMenu.setOnClickListener{
            val intent = Intent(this,AddItemAdmin::class.java)
            startActivity(intent)
        }
        binding.btnAllItem.setOnClickListener{
            val intent = Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.btnOutForDelivery.setOnClickListener{
            val intent = Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)
        }
        binding.btnProfile.setOnClickListener{
            val intent = Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }
    }
}