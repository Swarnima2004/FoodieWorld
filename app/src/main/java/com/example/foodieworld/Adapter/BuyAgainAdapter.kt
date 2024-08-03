package com.example.foodieworld.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodieworld.databinding.BuyAgainItemBinding

class BuyAgainAdapter (private val buyAgainFoodName: ArrayList<String>,
                       private val buyAgainFoodPrice: ArrayList<String> ,
                       private val buyAgainFoodImage: ArrayList<Int>): RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
        val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewHolder(binding)

    }

    override fun getItemCount(): Int {
      return buyAgainFoodName.size
    }

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
       holder.bind(buyAgainFoodName[position],buyAgainFoodPrice[position],buyAgainFoodImage[position])
    }

    class BuyAgainViewHolder(private val binding : BuyAgainItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(FoodName: String, FoodPrice: String, FoodImage: Int) {
         binding.buyFoodName.text = FoodName
         binding.buyFoodPrice.text = FoodPrice
            binding.buyAgainImage.setImageResource(FoodImage)
        }

    }
}

