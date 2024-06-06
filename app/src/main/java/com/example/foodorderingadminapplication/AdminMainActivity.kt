package com.example.foodorderingadminapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.foodorderingadminapplication.databinding.ActivityAdminMainBinding
import com.example.foodorderingadminapplication.model.AllMenu
import com.example.foodorderingadminapplication.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminMainActivity : AppCompatActivity() {


    private val binding: ActivityAdminMainBinding by lazy{
        ActivityAdminMainBinding.inflate(layoutInflater)
    }
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