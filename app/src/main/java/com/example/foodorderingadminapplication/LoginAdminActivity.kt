package com.example.foodorderingadminapplication

import android.app.Activity
import android.content.Intent
import android.credentials.CredentialManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foodorderingadminapplication.databinding.ActivityLoginAdminBinding
import com.example.foodorderingadminapplication.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
class LoginAdminActivity : AppCompatActivity() {

    private var binding: ActivityLoginAdminBinding? = null

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        auth = Firebase.auth
        database = Firebase.database.reference
        //initialize google Sign in
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)


        binding?.tvDontHaveAccount?.setOnClickListener {
            val intent= Intent(this, SignUpAdminActivity::class.java )
            startActivity(intent)
            finish()
        }
        binding?.btnLogin?.setOnClickListener {
            email = binding?.email?.text.toString()!!.trim()
            password = binding?.password?.text.toString()!!.trim()

            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this,"Please fill details", Toast.LENGTH_LONG).show()
            }
            else{
                createUserAccount(email, password)
            }

        }

        binding?.btnGoogle?.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }

    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                val user = auth.currentUser
                updateUI(user)
            }
            else{
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        val user = auth.currentUser
                        saveUserData(email, password)
                        updateUI(user)
                    } else{
                        Toast.makeText(this,"Authentication failed", Toast.LENGTH_LONG).show()
                        Log.d("Account","createAccount: Failure", task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData(email: String, password: String) {
        val user = UserModel("","",email, password)
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId.let{
            database.child("user").child(it!!).setValue(user)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, AdminMainActivity::class.java)
        intent.putExtra("dataUser", user?.uid)
        startActivity(intent)
        finish()
    }

    private val launcher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            if (task.isSuccessful){
                val account : GoogleSignInAccount = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                    if(authTask.isSuccessful){
                        //successfully sign in with google
                        Toast.makeText(this, "Successfully sign in with Google",Toast.LENGTH_LONG).show()
                        updateUI(authTask.result?.user)
                    }
                    else{
                        Toast.makeText(this, "Sign in with Google failed",Toast.LENGTH_LONG).show()
                    }
                }
            } else{
                Toast.makeText(this, "Sign in with Google failed",Toast.LENGTH_LONG).show()
            }
        }
    }
    //Check if user is already logged in
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!= null){
            val intent = Intent(this, AdminMainActivity::class.java)
            intent.putExtra("dataUser", currentUser.uid)
            startActivity(intent)
            finish()
        }
    }


}