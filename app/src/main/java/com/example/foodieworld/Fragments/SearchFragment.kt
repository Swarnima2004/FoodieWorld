package com.example.foodieworld.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodieworld.Adapter.menuAdapter
import com.example.foodieworld.databinding.FragmentSearchBinding
import com.example.foodieworld.model.menuItem
import com.google.android.material.search.SearchView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: menuAdapter
    private lateinit var database: FirebaseDatabase
    private val originalMenuItem = mutableListOf<menuItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        //retrive the data from database
        retrieveMenuItem()

        //setting the search view
        setUpSearchView()


        return binding.root

    }

    private fun retrieveMenuItem() {
        //  reference from the database
        database = FirebaseDatabase.getInstance()
        //reference to the menu node
        val foodReference: DatabaseReference = database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(menuItem::class.java)
                    menuItem?.let {
                        originalMenuItem.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun showAllMenu() {
        val filteredMenuItem = ArrayList(originalMenuItem)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: List<menuItem>) {
        adapter = menuAdapter(filteredMenuItem, requireContext())
        binding.menurecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.menurecyclerview.adapter = adapter
    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }

        })

    }

    private fun filterMenuItems(query: String) {
        val filteredMenuItems = originalMenuItem.filter {
            it.foodName?.contains(query, ignoreCase = true) == true
        }
        setAdapter(filteredMenuItems)
    }

    companion object {

    }

}







