package com.example.foodieworld.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuAdapter
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodieworld.Adapter.menuAdapter
import com.example.foodieworld.R
import com.example.foodieworld.databinding.FragmentSearchBinding
import com.example.foodieworld.databinding.MenuItemBinding


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private var searchMenuFood = listOf("burger","momos","Rasmalai","paneer tikka","coke","momos","Rasmalai","paneer tikka","coke")
    private var searchMenuPrice = listOf("$4","$7","$98","$31","$9","$7","$98","$31","$9")
    private var searchMenuImage = listOf(R.drawable.burgerfood,R.drawable.gulab_jamun,R.drawable.paneertikka, R.drawable.ras, R.drawable.food ,R.drawable.gulab_jamun,R.drawable.paneertikka, R.drawable.ras, R.drawable.food)
    private lateinit var adapter : menuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private val filterMenuFoodName = mutableListOf<String>()
    private val filterMenuPrice = mutableListOf<String>()
    private val filterMenuImage = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = FragmentSearchBinding.inflate(inflater, container, false)

        adapter = menuAdapter(filterMenuFoodName,filterMenuPrice,filterMenuImage)

        binding.menurecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.menurecyclerview.adapter = adapter

        //setting the search view
        setUpSearchView()
        //show ll menu items
        showAllMenu()

        return binding.root

    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
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

    private fun showAllMenu() {
        filterMenuFoodName.clear()
        filterMenuPrice.clear()
        filterMenuImage.clear()

        filterMenuFoodName.addAll(searchMenuFood)
        filterMenuPrice.addAll(searchMenuPrice)
        filterMenuImage.addAll(searchMenuImage)
        
        adapter.notifyDataSetChanged()
    }

   


        private fun filterMenuItems(query: String) {
          filterMenuFoodName.clear()
          filterMenuPrice.clear()
          filterMenuImage.clear()

            searchMenuFood.forEachIndexed { index, foodName ->
                if(foodName.contains(query, ignoreCase = true)){
                    filterMenuFoodName.add(foodName)
                    filterMenuPrice.add(searchMenuPrice[index])
                    filterMenuImage.add(searchMenuImage[index])
                }
            }
         adapter.notifyDataSetChanged()
       }

    companion object {

                }


}



