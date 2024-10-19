package com.example.foodieworld

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.foodieworld.databinding.ActivityFoodDetailsBinding

class foodDetails : AppCompatActivity() {
    private lateinit var binding : ActivityFoodDetailsBinding
    private var foodName : String?= null
    private var foodImage : String?= null
    private var foodDescription : String?= null
    private var foodIngredients : String?= null
    private var foodPrice : String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        foodName = intent.getStringExtra("MenuItemName")
        foodDescription = intent.getStringExtra("MenuItemDescription")
        foodIngredients = intent.getStringExtra("MenuItemIngredients")
        foodPrice = intent.getStringExtra("MenuItemPrice")
        foodImage = intent.getStringExtra("MenuItemImage")

        with(binding){
            deteilFoodName.text = foodName
            descriptionDetails.text = foodDescription
            ingredientsName.text = foodIngredients
            Glide.with(this@foodDetails).load(Uri.parse(foodImage)).into(foodDetailImage)

        }
        binding.backimagebtn.setOnClickListener {
            finish()
        }
    }
}