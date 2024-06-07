package com.example.foodorderingadminapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingadminapplication.adapter.PendingOrderAdapter
import com.example.foodorderingadminapplication.databinding.ActivityPendingOrderActivityBinding
import com.example.foodorderingadminapplication.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class PendingOrderActivity : AppCompatActivity(), PendingOrderAdapter.OnItemClicked {
    private var binding: ActivityPendingOrderActivityBinding? = null
    private var listOfName: MutableList<String> = mutableListOf()
    private var listOfTotalPrice: MutableList<String> = mutableListOf()
    private var listOfImageFirstFoodOrder: MutableList<String> = mutableListOf()
    private var listOfOrderItem: MutableList<OrderDetails> = mutableListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrderActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        // Initialization of database
        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("OrderDetails")
        getOrderDetails()
        binding?.btnBack?.setOnClickListener {
            finish()
        }
    }

    private fun getOrderDetails() {
        //retrieve order details from Firebase database
        listOfOrderItem.clear()
        databaseOrderDetails.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (orderSnapshot in snapshot.children){
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }
                    Log.d("hie","" + listOfOrderItem.size)
                }
                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun addDataToListForRecyclerView() {
        Log.d("hieb","" + listOfOrderItem.size)
        listOfOrderItem.forEach { orderItem ->
            orderItem.userName?.let {
                listOfName.add(it)
                Log.d("hieaa", "List of Names Size: ${listOfName.size}")
            }

            orderItem.totalPrice?.let {
                listOfTotalPrice.add(it)
            }

            orderItem.foodImages?.filterNot { it.isEmpty() }?.firstOrNull()?.let {
                listOfImageFirstFoodOrder.add(it)
            }
        }

        setAdapter()
    }

    private fun setAdapter() {
        binding?.rvPendingOrder?.layoutManager = LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(listOfName, listOfTotalPrice, listOfImageFirstFoodOrder, this, this)
        binding?.rvPendingOrder?.adapter = adapter
    }

    override fun onItemClickListener(position: Int) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        val userOrderDetails = listOfOrderItem[position]
        intent.putExtra("userOrderDetails",userOrderDetails)
        startActivity(intent)
    }

    override fun onItemAcceptClickListener(position: Int) {
        // handle item accept and update database
        val childItemPushKey = listOfOrderItem[position].itemPushKey
        val clickItemOrderReference = childItemPushKey?.let{
            database.reference.child("OrderDetails").child(it)
        }
        clickItemOrderReference?.child("orderAccepted")?.setValue(true)
        updateOrderAcceptStatus(position)
    }
    private fun updateOrderAcceptStatus(position: Int){
        // update order acceptance iin user's Buy History and OrderDetails
        val userIdOfClickedItem = listOfOrderItem[position].userUid
        val pushKeyOfClickedItem = listOfOrderItem[position].itemPushKey
        val buyHistoryReference = database.reference.child("user").child(userIdOfClickedItem!!).child("BuyHistory")
            .child(pushKeyOfClickedItem!!)
        buyHistoryReference.child("orderAccepted").setValue(true)
        databaseOrderDetails.child(pushKeyOfClickedItem).child("orderAccepted").setValue(true)
    }


    override fun onItemDispatchClickListener(position: Int) {
        val dispatchItemPushKey = listOfOrderItem[position].itemPushKey
        val dispatchItemOrderReference = database.reference.child("CompleteOrder").child(dispatchItemPushKey!!)
        dispatchItemOrderReference.setValue(listOfOrderItem[position])
            .addOnSuccessListener {
                deleteThisItemFromOrderDetails(dispatchItemPushKey)
            }
    }
    private fun  deleteThisItemFromOrderDetails(dispatchItemPushKey: String){
        val orderDetailsItemsReference = database.reference.child("OrderDetails").child(dispatchItemPushKey)
        orderDetailsItemsReference.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Order is dispatched", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { Toast.makeText(this, "Order is not dispatched", Toast.LENGTH_SHORT).show() }
    }
}