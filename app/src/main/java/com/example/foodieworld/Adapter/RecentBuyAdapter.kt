package com.example.foodieworld.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodieworld.databinding.RecentorderitemsBinding


class RecentBuyAdapter(
    private var context: Context,
    private var foodNameList: ArrayList<String>,
    private var foodImagesList: ArrayList<String>,
    private var foodPriceList: ArrayList<String>,
    private var foodQuantityList: ArrayList<Int>
) : RecyclerView.Adapter<RecentBuyAdapter.RecentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val binding =
            RecentorderitemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentViewHolder(binding)
    }

    override fun getItemCount(): Int = foodNameList.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class RecentViewHolder(private val binding: RecentorderitemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
             binding.apply {
                 orderFoodName.text = foodNameList[position]
                 OrderFoodPrice.text = foodPriceList[position]
                 orderedFoodQuantity.text = foodQuantityList[position].toString()
                 val uriString = foodImagesList[position]
                 val uri = Uri.parse(uriString)
                 Glide.with(context).load(uri).into(orderFoodImage)
             }
        }

    }
}