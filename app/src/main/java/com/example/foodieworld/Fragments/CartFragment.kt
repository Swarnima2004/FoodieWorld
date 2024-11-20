package com.example.foodieworld.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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


@Suppress("UNCHECKED_CAST")
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodImageUri: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var userId: String
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)

        //initialise
        auth = FirebaseAuth.getInstance()
        retrieveCartItems()

        binding.proceedbtn.setOnClickListener {
            // get the details of the ordered item
            getDetailOfOrder()

        }
        return binding.root
    }

    private fun getDetailOfOrder() {
        val orderIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("Cartitems")
        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodimage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngridents = mutableListOf<String>()
        //no. of items in card
        val foodQuantities = cartAdapter.getUpdateditem()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodsnapShot in snapshot.children) {
                    //get the cart items in respective list
                    val orderItems = foodsnapShot.getValue(CartItems::class.java)
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.fooddescription?.let { foodDescription.add(it) }
                    orderItems?.foodImage?.let { foodimage.add(it) }
                    orderItems?.foodIngredient?.let { foodIngridents.add(it) }
                }
                orderNow(
                    foodName,
                    foodPrice,
                    foodDescription,
                    foodimage,
                    foodIngridents,
                    foodQuantities
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Order Failed. Please try again😊",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodimage: MutableList<String>,
        foodIngridents: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), buyAndPay::class.java)
            intent.putExtra("FoodItemName", foodName as ArrayList<String>)
            intent.putExtra("FoodItemPrice", foodPrice as ArrayList<String>)
            intent.putExtra("FoodItemImage", foodimage as ArrayList<String>)
            intent.putExtra("FoodItemDescription", foodDescription as ArrayList<String>)
            intent.putExtra("FoodItemIngredients", foodIngridents as ArrayList<String>)
            intent.putExtra("FoodItemQualities", foodQuantities as ArrayList<Int>)


            startActivity(intent)
        }

    }

    private fun retrieveCartItems() {
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodReference: DatabaseReference =
            database.reference.child("user").child(userId).child("Cartitems")
        //list to store cart items

        foodNames = mutableListOf()
        foodDescriptions = mutableListOf()
        foodIngredients = mutableListOf()
        foodImageUri = mutableListOf()
        foodPrices = mutableListOf()
        quantity = mutableListOf()

        //fetching data from database
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodsnapshot in snapshot.children) {
                    val cartItems = foodsnapshot.getValue(CartItems::class.java)
                    //add card item details to the list
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.fooddescription?.let { foodDescriptions.add(it) }
                    cartItems?.foodImage?.let { foodImageUri.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                    cartItems?.foodIngredient?.let { foodIngredients.add(it) }
                }
                setAdapter()
            }

            private fun setAdapter() {
                cartAdapter = CartAdapter(
                    requireContext(),
                    foodNames,
                    foodPrices,
                    foodDescriptions,
                    foodImageUri,
                    quantity,
                    foodIngredients
                )
                binding.Recycler.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.Recycler.adapter = cartAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Unable to fetch data", Toast.LENGTH_SHORT).show()
            }

        })

    }


}