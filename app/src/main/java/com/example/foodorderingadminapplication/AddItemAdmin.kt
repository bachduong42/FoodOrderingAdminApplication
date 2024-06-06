package com.example.foodorderingadminapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.foodorderingadminapplication.databinding.ActivityAddItemAdminBinding

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
        binding.selectedImage.setOnClickListener {
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly));
        }
        binding.btnBack.setOnClickListener{
            finish();
        }
    }

    val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if(uri!=null){
            binding.selectedImage.setImageURI(uri);
        }

    }
}