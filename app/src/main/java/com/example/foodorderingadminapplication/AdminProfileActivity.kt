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


}