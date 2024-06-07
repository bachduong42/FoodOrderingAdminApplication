package com.example.foodorderingadminapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.foodorderingadminapplication.databinding.ActivityAdminProfileBinding
import com.example.foodorderingadminapplication.model.UserModel

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
    private fun setDataToTextView(
        ownerName: String?,
        email: String?,
        password: String?,
        address: String?,
        phone: String?
    ) {
        binding.etNameOwner.setText(ownerName)
        binding.etEmailOwner.setText(email)
        binding.etPasswordOwner.setText(password)
        binding.etAddressOwner.setText(address)
        binding.etPhoneOwner.setText(phone)
    }

    private fun updateAdminData() {
        var updateName = binding.etNameOwner.text.toString()
        var updateEmail = binding.etEmailOwner.text.toString()
        var updatePassword = binding.etPasswordOwner.text.toString()
        var updateAddress = binding.etAddressOwner.text.toString()
        var updatePhone = binding.etPhoneOwner.text.toString()
        var userModel = UserModel(updateName,null,updateEmail, updatePassword,updatePhone, updateAddress)
        adminReference.setValue(userModel).addOnSuccessListener {
            Toast.makeText(this, "Profile update successful", Toast.LENGTH_SHORT).show()
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        }.addOnFailureListener {
            Toast.makeText(this, "Profile update fail", Toast.LENGTH_SHORT).show()
        }
    }

}