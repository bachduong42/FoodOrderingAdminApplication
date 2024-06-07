package com.example.foodorderingadminapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.foodorderingadminapplication.databinding.ActivityAdminMainBinding
import com.example.foodorderingadminapplication.model.AllMenu
import com.example.foodorderingadminapplication.model.OrderDetails
import com.example.foodorderingadminapplication.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminMainActivity : AppCompatActivity() {


    private val binding: ActivityAdminMainBinding by lazy{
        ActivityAdminMainBinding.inflate(layoutInflater)
    }

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var completedOrderReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var user : UserModel? =null
        var uidUser = intent.getStringExtra("dataUser")
        getDataUserFromDatabase(uidUser!!){
            if (it != null) {
                user = it
                binding.btnAllItem.setOnClickListener{
                    val intent = Intent(this,AllItemActivity::class.java)
                    intent.putExtra("user",user)
                    startActivity(intent)
                }
            }
        }
        Log.d("user2",""+user?.name)

        binding.btnAddMenu.setOnClickListener{
            val intent = Intent(this,AddItemAdmin::class.java)
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
        binding.tvPendingOrder.setOnClickListener {
            val intent = Intent(this, PendingOrderActivity::class.java)
            startActivity(intent)
        }

        pendingOrders()
        completedOrders()
        wholeTimeEarning()
    }

    private fun wholeTimeEarning() {
        var listTotalPay = mutableListOf<Int>()
        completedOrderReference = database.reference.child("CompleteOrder")
        completedOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(orderSnapshot in snapshot.children){
                    var completeOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.totalPrice?.replace(",00$","")?.toIntOrNull()
                        ?.let { i->
                            listTotalPay.add(i)
                        }
                }
                binding.tvWholeTimeEarning.text = listTotalPay.sum().toString() + "$"
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun completedOrders() {
        database = FirebaseDatabase.getInstance()
        var completedOrderReference = database.reference.child("CompleteOrder")
        var completedOrderItemCount = 0
        completedOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                completedOrderItemCount = snapshot.childrenCount.toInt()
                binding.tvCompletedItemCount.text = completedOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun pendingOrders() {
        database = FirebaseDatabase.getInstance()
        var pendingOrderReference = database.reference.child("OrderDetails")
        var pendingOrderItemCount = 0
        pendingOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.tvPendingItemCount.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getDataUserFromDatabase(uid: String, callback: (UserModel?) -> Unit) {
        val db = FirebaseDatabase.getInstance()
        val userRef = db.getReference("user").child(uid)

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userModel: UserModel? = if (dataSnapshot.exists()) {
                    val name = dataSnapshot.child("name").getValue(String::class.java)
                    val nameOfRestaurant = dataSnapshot.child("nameOfRestaurant").getValue(String::class.java)
                    val email = dataSnapshot.child("email").getValue(String::class.java)
                    val password = dataSnapshot.child("password").getValue(String::class.java)
                    UserModel(name, nameOfRestaurant, email, password)


                } else {
                    null
                }
                Log.d("user1","hi"+ userModel?.name)
                callback(userModel)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(null)
            }
        })
    }

}