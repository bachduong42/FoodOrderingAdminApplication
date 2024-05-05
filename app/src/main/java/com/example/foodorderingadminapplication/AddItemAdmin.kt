package com.example.foodorderingadminapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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