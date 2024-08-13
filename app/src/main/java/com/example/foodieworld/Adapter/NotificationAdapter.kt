package com.example.foodieworld.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodieworld.databinding.NotificationitemsBinding

class NotificationAdapter(private val notification: ArrayList<String>, private val NotificationImage: ArrayList<Int>) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    inner class NotificationViewHolder (private val binding: NotificationitemsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
           binding.apply {
               notificationText.text = notification[position]
               notificationImage.setImageResource(NotificationImage[position])
           }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = NotificationitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotificationViewHolder(binding)
    }

    override fun getItemCount(): Int = notification.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(position)
    }
}