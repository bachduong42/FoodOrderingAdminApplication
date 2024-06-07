package com.example.foodorderingadminapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingadminapplication.adapter.MenuItemAdapter
import com.example.foodorderingadminapplication.databinding.ActivityAllItemBinding
import com.example.foodorderingadminapplication.model.AllMenu
import com.example.foodorderingadminapplication.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllItemActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private var menuItems: ArrayList<AllMenu> = ArrayList()
    private lateinit var userModel: UserModel
    private val binding: ActivityAllItemBinding by lazy{
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var user = intent.getParcelableExtra<UserModel>("user")
        userModel = UserModel(user?.email, user?.name,user?.nameOfRestaurant, user?.password)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrivieveMenuItem()

        binding.btnBack.setOnClickListener{
            finish();
        }
    }

    private fun retrivieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef = database.reference.child("menu")
        //fetch data from database
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear existing data
                menuItems.clear()
                //loop for through each food item
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                        setAdapter()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("databaseError","Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {

        val adapter = MenuItemAdapter(menuItems,this@AllItemActivity,databaseReference, userModel.nameOfRestaurant!!)
        binding.rvItems.layoutManager= LinearLayoutManager(this)
        binding.rvItems.adapter= adapter
    }
}