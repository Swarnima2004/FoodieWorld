package com.example.foodieworld.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodieworld.Adapter.CartAdapter
import com.example.foodieworld.R
import com.example.foodieworld.buyAndPay
import com.example.foodieworld.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false)

        val cartFoodName = listOf("burger","momos","Rasmalai","paneer tikka","coke")
        val cartItemPrice = listOf("$4","$7","$98","$31","$9")
        val cartImages = listOf(R.drawable.burgerfood,R.drawable.gulab_jamun,R.drawable.paneertikka, R.drawable.ras, R.drawable.food)
        val adapter = CartAdapter(ArrayList(cartFoodName),ArrayList(cartItemPrice),ArrayList(cartImages))
        binding.Recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.Recycler.adapter = adapter

        binding.proceedbtn.setOnClickListener {
            val intent = Intent(requireContext(),buyAndPay::class.java)
            startActivity(intent)

        }
        return binding.root
    }



}