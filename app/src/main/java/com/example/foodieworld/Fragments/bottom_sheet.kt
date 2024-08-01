package com.example.foodieworld.Fragments

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.foodieworld.Adapter.menuAdapter
import com.example.foodieworld.R

import com.example.foodieworld.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class bottom_sheet : BottomSheetDialogFragment() {
   private lateinit var binding: FragmentBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        binding.backbtn.setOnClickListener {
            dismiss()
        }

        val menuFoodName = listOf("burger","momos","Rasmalai","paneer tikka","coke","momos","Rasmalai","paneer tikka","coke")
        val menuItemPrice = listOf("$4","$7","$98","$31","$9","$7","$98","$31","$9")
        val menuImages = listOf(R.drawable.burgerfood,R.drawable.gulab_jamun,R.drawable.paneertikka, R.drawable.ras, R.drawable.food ,R.drawable.gulab_jamun,R.drawable.paneertikka, R.drawable.ras, R.drawable.food)
        val adapter = menuAdapter(ArrayList(menuFoodName),ArrayList(menuItemPrice),ArrayList(menuImages))
        binding.menurecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.menurecyclerview.adapter = adapter
        return binding.root
    }

}




