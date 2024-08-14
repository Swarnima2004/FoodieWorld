package com.example.foodieworld.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodieworld.R
import com.example.foodieworld.databinding.ActivityHomePageBinding
import com.example.foodieworld.databinding.FragmentConfirmBottomSheetBinding
import com.example.foodieworld.home_page
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class confirmBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentConfirmBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmBottomSheetBinding.inflate(layoutInflater,container,false)
        binding.goToHomebtn.setOnClickListener {
           val intent = Intent(requireContext(),home_page::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {

    }
}