package com.example.foodieworld.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodieworld.Adapter.PopularAdapter
import com.example.foodieworld.Adapter.menuAdapter
import com.example.foodieworld.R
import com.example.foodieworld.databinding.FragmentHomeBinding
import com.example.foodieworld.model.menuItem
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database : FirebaseDatabase
    private lateinit var menuItems: MutableList<menuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.Viewbtn.setOnClickListener {
            val bottomSheetDialog = bottom_sheet()
            bottomSheetDialog.show(parentFragmentManager, "test")
        }
        //retrieve and display popular food items
        retrievePopularItems()
        return binding.root


    }

    private fun retrievePopularItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef : DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        //retrieve menu items from the database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapShots in snapshot.children){
                    val menuItem = foodSnapShots.getValue(menuItem::class.java)
                    menuItem?.let{ menuItems.add(it)}
                }
                //display random popular items
                randomPopularItems()
            }



            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Unable to fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun randomPopularItems() {
        //create a shuffle list of the items
        val index = menuItems.indices.toList().shuffled()
        val numItem = 6
        val subsetMenuItem = index.take(numItem).map { menuItems[it] }
        setPopularItemsAdapter(subsetMenuItem)
    }

    private fun setPopularItemsAdapter(subsetMenuItem: List<menuItem>) {
        val adapter = menuAdapter(subsetMenuItem , requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {

            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })


    }
}


