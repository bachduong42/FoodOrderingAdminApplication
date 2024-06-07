package com.example.foodorderingadminapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingadminapplication.adapter.DeliveryAdapter
import com.example.foodorderingadminapplication.databinding.ActivityOutForDeliveryBinding
import com.example.foodorderingadminapplication.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding : ActivityOutForDeliveryBinding by lazy{
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit var database: FirebaseDatabase
    private var listOfCompleteOrderList: ArrayList<OrderDetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            finish();
        }

        // retrieve and display completed order
        retreiveCompleteOrderDetail()

        val customerName = arrayListOf(
            "Bach Duong",
            "Le Nguyen",
            "Bao Chau"
        )
        val moneyStatus= arrayListOf(
            "Received",
            "Not Received",
            "Pending"
        )

    }

    private fun retreiveCompleteOrderDetail() {
        //Initialize Firebase database
        database = FirebaseDatabase.getInstance()
        val completeOrderReference = database.reference.child("CompleteOrder")
            .orderByChild("currentItem")
        completeOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfCompleteOrderList.clear()
                for (orderSnapshot in snapshot.children){
                    val completedOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completedOrder?.let {
                        listOfCompleteOrderList.add(it)
                    }
                }

                listOfCompleteOrderList.reverse()

                setDataIntoRecyclerView()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataIntoRecyclerView() {
        val customerName = mutableListOf<String>()
        val moneyStatus = mutableListOf<Boolean>()

        for (order in listOfCompleteOrderList){
            order.userName?.let {
                customerName.add(it)
            }
            moneyStatus.add(order.paymentReceived)
        }
        val adapter = DeliveryAdapter(customerName, moneyStatus)
        binding.rvOutForDelivery.adapter = adapter
        binding.rvOutForDelivery.layoutManager= LinearLayoutManager(this)
    }
}