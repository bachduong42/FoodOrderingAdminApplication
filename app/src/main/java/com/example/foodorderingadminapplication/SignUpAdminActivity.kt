package com.midterm.foododeringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.foodorderingadminapplication.R
import com.example.foodorderingadminapplication.databinding.ActivitySignUpAdminBinding

class SignUpAdminActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var userName: String
    private lateinit var nameOfRestaurant: String
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignUpAdminBinding by lazy{
        ActivitySignUpAdminBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //initialize Firebase Auth
        auth = Firebase.auth
        //initialize Firebase database
        database = Firebase.database.reference

        binding.btnSignupAdmin.setOnClickListener {
            email = binding.etEmail.text.toString().trim()
            password = binding.etPassword.text.toString().trim()
            userName = binding.etNameOwner.text.toString().trim()
            nameOfRestaurant = binding.etNameRestaurant.text.toString().trim()

            if (userName.isBlank() || email.isBlank() || password.isBlank() || nameOfRestaurant.isBlank()){
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_LONG).show()
            } else{
                createAccount(email, password)
            }

        }

    }

}