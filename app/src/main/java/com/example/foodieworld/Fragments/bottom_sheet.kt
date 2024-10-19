package com.example.foodieworld.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodieworld.Adapter.menuAdapter
import com.example.foodieworld.databinding.FragmentBottomSheetBinding
import com.example.foodieworld.model.menuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class bottom_sheet : BottomSheetDialogFragment() {

    private lateinit var menuItems: MutableList<menuItem>
    private lateinit var database: FirebaseDatabase
    private lateinit var binding: FragmentBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        binding.backbtn.setOnClickListener {
            dismiss()
        }
        retrieveMenuItem()



        return binding.root
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodref: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()
        foodref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(menuItem::class.java)
                    menuItem?.let { menuItems.add(it) }
                }
                Log.d("ITEMS","onDataChange: Data Received")
                // once the data is receive,set to adapter
                setAdapter()
            }



            override fun onCancelled(error: DatabaseError) {
               
            }

        })

    }
    private fun setAdapter() {
       if(menuItems.isNotEmpty()){
           val adapter = menuAdapter(menuItems, requireContext())
           binding.menurecyclerview.layoutManager = LinearLayoutManager(requireContext())
           binding.menurecyclerview.adapter = adapter
           Log.d("ITEMS","setAdapter: datea set")
       }else{
           Log.d("ITEMS","setAdapter: data not set")
       }
    }


}




