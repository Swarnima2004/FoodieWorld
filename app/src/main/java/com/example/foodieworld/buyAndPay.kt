package com.example.foodieworld

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodieworld.Fragments.confirmBottomSheet
import com.example.foodieworld.databinding.ActivityBuyAndPayBinding
import com.example.foodieworld.databinding.BuyAgainItemBinding

class buyAndPay : AppCompatActivity() {
    private lateinit var binding:ActivityBuyAndPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBuyAndPayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.placeorderbtn.setOnClickListener {
            val bottomSheetDialog = confirmBottomSheet()
            bottomSheetDialog.show(supportFragmentManager,"test")
        }
    }
}