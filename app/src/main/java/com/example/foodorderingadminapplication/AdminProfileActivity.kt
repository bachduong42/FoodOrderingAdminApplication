package com.example.foodorderingadminapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodorderingadminapplication.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy{
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val adminId = auth.currentUser?.uid?:""
        adminReference = database.reference.child("user").child(adminId)

        binding.btnSaveInfor.setOnClickListener {
            updateAdminData()
        }

        binding.btnBack.setOnClickListener{
            finish();
        }
        retrieveUserData()
    }
    private fun retrieveUserData() {
        adminReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var ownerName = snapshot.child("name").getValue(String::class.java)
                    var email = snapshot.child("email").getValue(String::class.java)
                    var password = snapshot.child("password").getValue(String::class.java)
                    var address = snapshot.child("address").getValue(String::class.java)
                    var phone = snapshot.child("phone").getValue(String::class.java)
                    setDataToTextView(ownerName,email,password,address,phone)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}