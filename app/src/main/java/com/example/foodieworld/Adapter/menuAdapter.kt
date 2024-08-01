package com.example.foodieworld.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodieworld.databinding.MenuItemBinding

class menuAdapter(private val menuItemName:MutableList<String>, private val menuItemPrice:MutableList<String>, private val menuItemImage:MutableList<Int>): RecyclerView.Adapter<menuAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return menuItemName.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MenuViewHolder(private val binding: MenuItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
        binding.apply {
           menuFood.text= menuItemName[position]
            menuPrice.text = menuItemPrice[position]
            menuImage.setImageResource(menuItemImage[position])

        }

        }

    }
}