package com.example.foodorderingadminapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foodorderingadminapplication.databinding.ActivityAddItemAdminBinding
import com.example.foodorderingadminapplication.model.AllMenu

class AddItemAdmin : AppCompatActivity() {
    private val binding:ActivityAddItemAdminBinding by lazy {
        ActivityAddItemAdminBinding.inflate(layoutInflater);
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root);
        //initialize FireBase
        auth = FirebaseAuth.getInstance()
        //initialize FireBase database instance
        database = FirebaseDatabase.getInstance()
        binding.btnAddItem.setOnClickListener {
            //get data from Filed
            foodName = binding.edtNameItem.text.toString().trim()
            foodPrice = binding.edtPriceItem.text.toString().trim()
            foodDescription = binding.etDescription.text.toString().trim()
            foodIngredient = binding.etIngredients.text.toString().trim()

            if (foodDescription.isBlank()||foodIngredient.isBlank()||foodName.isBlank()||foodPrice.isBlank()){
                Toast.makeText(this,"Fill all details!", Toast.LENGTH_LONG).show()
            } else{
                uploadData()
                Toast.makeText(this,"Item add successfully!", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnSelectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.btnBack.setOnClickListener{
            finish();
        }
    }
    private fun uploadData() {
        // get a reference to the "menu" node in the database
        val menuRef = database.getReference("menu")
        // generate a unique key for the new menu item
        val newItemKey = menuRef.push().key
        if(foodImage !=null){
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.ipg")
            val uploadTask = imageRef.putFile(foodImage!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                        downloadUri ->
                    // create new item
                    val newItem = AllMenu(
                        foodName,foodPrice,foodDescription,downloadUri.toString(),foodIngredient
                    )
                    newItemKey?.let{
                            key ->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"Data upload successfully!", Toast.LENGTH_LONG).show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this,"Data upload Failed!", Toast.LENGTH_LONG).show()
                            }
                    }
                }
                    .addOnFailureListener {
                        Toast.makeText(this,"Image upload Failed!", Toast.LENGTH_LONG).show()
                    }
            }
        } else{
            Toast.makeText(this,"Please select image", Toast.LENGTH_LONG).show()
        }
    }
    val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if(uri!=null){
            binding.selectedImage.setImageURI(uri);
        }

    }
}