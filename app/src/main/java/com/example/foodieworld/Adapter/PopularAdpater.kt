package com.example.foodieworld.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodieworld.Adapter.PopularAdapter.PopularViewHolder
import com.example.foodieworld.databinding.PopularFoodBinding

class PopularAdapter (private val items:List<String>,private val prices :List<String>,private val image:List<Int>): RecyclerView.Adapter<PopularViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularViewHolder {
        return PopularViewHolder(PopularFoodBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder:PopularViewHolder, position: Int) {

            val item = items[position]
            val images = image[position]
            val price = prices[position]
            holder.bind(item, price, images)




    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PopularViewHolder (private val binding : PopularFoodBinding) : RecyclerView.ViewHolder(binding.root){
      private val imagesView = binding.imageView4
        fun bind(item: String,price:String, images: Int) {
         binding.popularFood.text = item
            binding.popularPrice.text = price
            imagesView.setImageResource(images)

        }

    }

}