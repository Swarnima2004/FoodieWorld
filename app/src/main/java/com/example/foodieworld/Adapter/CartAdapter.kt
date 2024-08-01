package com.example.foodieworld.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodieworld.databinding.CartItemBinding

class CartAdapter(private val cartItems:MutableList<String>,private val cartItemPrice:MutableList<String> , private val CartImage:MutableList<Int>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val itemQuantities = IntArray(cartItems.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
      val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

   inner class CartViewHolder( private val binding : CartItemBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
         binding.apply {
             val quantity = itemQuantities[position]
             cartFoodName.text = cartItems[position]
             cartFoodPrice.text = cartItemPrice[position]
             cartImage.setImageResource(CartImage[position])
             CartItenQuantity.text = quantity.toString()

             minusbtn.setOnClickListener{
                 decreaseQuantity(position)
             }
             DeleteButton.setOnClickListener {
                 val itemPosition = adapterPosition
                 if(itemPosition != RecyclerView.NO_POSITION){
                     DeleteItem(itemPosition)
                 }

             }

             addbtn.setOnClickListener {
                 increaseQuantity(position)
             }




         }
        }
       private fun decreaseQuantity(position :Int){
           if(itemQuantities[position] >1){
               itemQuantities[position]--
               binding.CartItenQuantity.text = itemQuantities[position].toString()
           }
       }

       private fun increaseQuantity(position :Int){
           if(itemQuantities[position] <10){
               itemQuantities[position]++
               binding.CartItenQuantity.text = itemQuantities[position].toString()
           }
       }

       private fun DeleteItem(position : Int){
           cartItems.removeAt(position)
           cartItemPrice.removeAt(position)
           CartImage.removeAt(position)
           notifyItemRemoved(position)
           notifyItemRangeChanged(position, cartItems.size)
       }

    }

}