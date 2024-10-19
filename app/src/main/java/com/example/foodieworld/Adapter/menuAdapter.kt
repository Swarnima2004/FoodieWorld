package com.example.foodieworld.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodieworld.databinding.MenuItemBinding
import com.example.foodieworld.foodDetails
import com.example.foodieworld.model.menuItem

class menuAdapter(
    private val menuItems : List<menuItem>,
    private val requireContext : Context)
    : RecyclerView.Adapter<menuAdapter.MenuViewHolder>() {



    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MenuViewHolder(private val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    openDetailActivity(position)
                }

            }
        }

        private fun openDetailActivity(position: Int) {
            val menuItem = menuItems[position]
            //intent to open the detail activity and pass data
             val intent = Intent(requireContext,foodDetails::class.java).apply {
                 putExtra("MenuItemName",menuItem.foodName)
                 putExtra("MenuItemImage",menuItem.foodImage)
                 putExtra("MenuItemDescription",menuItem.foodDescription)
                 putExtra("MenuItemIngredients",menuItem.foodIngredient)
                 putExtra("MenuItemPrice",menuItem.foodPrice)
             }

            //start the detail activity
            requireContext.startActivity(intent)


        }

        //data in recycler view item

        fun bind(position: Int) {
            val menuItem = menuItems[position]
            binding.apply {
                menuFood.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice
                val uri = Uri.parse(menuItem.foodImage)
                Glide.with(requireContext).load(uri).into(menuImage)


            }

        }

    }


}
