package com.example.foodieworld

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodieworld.databinding.ActivityFoodDetailsBinding

class foodDetails : AppCompatActivity() {
    private lateinit var binding : ActivityFoodDetailsBinding
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
        val foodName = intent.getStringExtra("menuItemName")
        val foodImage = intent.getIntExtra("menuItemImage" , 0)
        binding.deteilFoodName.text = foodName
        binding.foodDetailImage.setImageResource(foodImage)
        binding.backimagebtn.setOnClickListener {
            finish()
        }
    }
}