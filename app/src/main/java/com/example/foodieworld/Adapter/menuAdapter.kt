package com.example.foodieworld.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodieworld.databinding.MenuItemBinding
import com.example.foodieworld.foodDetails

class menuAdapter(private val menuItemName:MutableList<String>, private val menuItemPrice:MutableList<String>, private val menuItemImage:MutableList<Int>, private val requireContext : Context): RecyclerView.Adapter<menuAdapter.MenuViewHolder>() {

    private val itemClickListener: OnClickListener? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return menuItemName.size
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
                    itemClickListener?.onItemClick(position)

                    //set on click to open the food details
                    val intent = Intent(requireContext, foodDetails::class.java)
                    intent.putExtra("menuItemName", menuItemName.get(position))
                    intent.putExtra("menuItemImage", menuItemImage.get(position))
                    requireContext.startActivity(intent)
                }
            }
        }

        fun bind(position: Int) {
            binding.apply {
                menuFood.text = menuItemName[position]
                menuPrice.text = menuItemPrice[position]
                menuImage.setImageResource(menuItemImage[position])


            }

        }

    }

    interface OnClickListener {
        fun onItemClick(position: Int) {

        }
    }
}
