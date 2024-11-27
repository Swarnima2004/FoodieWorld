package com.example.foodieworld

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodieworld.Adapter.RecentBuyAdapter
import com.example.foodieworld.databinding.ActivityRecentlyBuyItemBinding
import com.example.foodieworld.model.orderDetails

class recentlyBuyItem : AppCompatActivity() {

    private val binding: ActivityRecentlyBuyItemBinding by lazy {
        ActivityRecentlyBuyItemBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodNames: ArrayList<String>
    private lateinit var allFoodImages: ArrayList<String>
    private lateinit var allFoodPrice: ArrayList<String>
    private lateinit var allFoodQuantities: ArrayList<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.backbutton.setOnClickListener {
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recentOrderItems =
            intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<orderDetails>
        recentOrderItems?.let { OrderDetails ->
            if (OrderDetails.isNotEmpty()) {
                val recentOrderItem = OrderDetails[0]
                allFoodNames = recentOrderItem.foodName as ArrayList<String>
                allFoodImages = recentOrderItem.foodImages as ArrayList<String>
                allFoodPrice = recentOrderItem.foodPrices as ArrayList<String>
                allFoodQuantities = recentOrderItem.foodQuantities as ArrayList<Int>
            }
        }
        setAdpater()
    }

    private fun setAdpater() {
       val rv = binding.orderedFoodList
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(this,allFoodNames,allFoodImages,allFoodPrice,allFoodQuantities)
        rv.adapter = adapter
    }
}