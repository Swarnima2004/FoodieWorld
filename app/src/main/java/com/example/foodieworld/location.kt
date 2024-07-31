package com.example.foodieworld

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodieworld.databinding.ActivityLocationBinding

class location : AppCompatActivity() {
    private val binding : ActivityLocationBinding by lazy{
        ActivityLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val locations = arrayOf("Gorakhpur","Agra","Delhi","Lucknow")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,locations)
        val autoCompleteTextView  = binding.locationList
        autoCompleteTextView.setAdapter(adapter)
    }
}