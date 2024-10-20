package com.example.foodieworld.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodieworld.Adapter.CartAdapter
import com.example.foodieworld.buyAndPay
import com.example.foodieworld.databinding.FragmentCartBinding
import com.example.foodieworld.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var auth :FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodImageUri: MutableList<String>
    private lateinit var foodIngredients : MutableList<String>
    private lateinit var quantity : MutableList<Int>
    private lateinit var userId : String
    private lateinit var cartAdapter: CartAdapter

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

        //initialise
        auth = FirebaseAuth.getInstance()
        retrieveCartItems()

        binding.proceedbtn.setOnClickListener {
            val intent = Intent(requireContext(),buyAndPay::class.java)
            startActivity(intent)

        }
        return binding.root
    }

    private fun retrieveCartItems() {
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid?:""
        val foodReference :DatabaseReference = database.reference.child("user").child(userId).child("Cartitems")
        //list to store cart items

        foodNames = mutableListOf()
        foodDescriptions = mutableListOf()
        foodIngredients = mutableListOf()
        foodImageUri = mutableListOf()
        foodPrices = mutableListOf()
        quantity = mutableListOf()

        //fetching data from database
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodsnapshot in snapshot.children){
                    val cartItems = foodsnapshot.getValue(CartItems::class.java)
                    //add card item details to the list
                    cartItems?.foodName?.let{foodNames.add(it)}
                    cartItems?.foodPrice?.let{foodPrices.add(it)}
                    cartItems?.fooddescription?.let{foodDescriptions.add(it)}
                    cartItems?.foodImage?.let{foodImageUri.add(it)}
                    cartItems?.foodQuantity?.let{quantity.add(it)}
                    cartItems?.foodIngredient?.let{foodIngredients.add(it)}
                }
                setAdapter()
            }

            private fun setAdapter() {
                val adapter = CartAdapter(requireContext(),foodNames,foodPrices,foodDescriptions,foodImageUri,quantity,foodIngredients)
                binding.Recycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                binding.Recycler.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Unable to fetch data", Toast.LENGTH_SHORT).show()
            }

        })

    }


}