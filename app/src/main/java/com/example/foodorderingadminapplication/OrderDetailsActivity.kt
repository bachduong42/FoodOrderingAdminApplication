package com.example.foodorderingadminapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingadminapplication.adapter.OrderDetailsAdapter
import com.example.foodorderingadminapplication.databinding.ActivityOrderDetailsBinding
import com.example.foodorderingadminapplication.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private var binding: ActivityOrderDetailsBinding? = null

    private var userName: String? = null
    private var address: String? = null
    private var phoneNumber: String? = null
    private var totalPrice: String? = null
    private var foodNames :ArrayList<String> = arrayListOf()
    private var foodImages :ArrayList<String> = arrayListOf()
    private var foodQuantity :ArrayList<String> = arrayListOf()
    private var foodPrices :ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnBack?.setOnClickListener {
            finish()
        }
        getDataFromIntent()

    }

    private fun getDataFromIntent() {
        val receivedOrderDetails = intent.getParcelableExtra<OrderDetails>("userOrderDetails")
        receivedOrderDetails?.let {
            userName = it.userName
            foodNames = it.foodNames as ArrayList<String>
            foodImages = it.foodImages as ArrayList<String>
            foodQuantity = it.foodQuantities as ArrayList<String>
            address = it.address
            phoneNumber = it.phoneNumber
            foodPrices = it.foodPrices as ArrayList<String>
            totalPrice = it.totalPrice

            setUserDetail()
            setAdapter()
        }
    }

    private fun setAdapter() {
        binding?.rvDetails?.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this, foodNames, foodImages, foodQuantity, foodPrices)
        binding?.rvDetails?.adapter = adapter
    }

    private fun setUserDetail() {
        binding?.etAddress?.setText(address)
        binding?.etName?.setText(userName)
        binding?.etPhone?.setText(phoneNumber)
        binding?.etTotal?.setText(totalPrice)
    }
}