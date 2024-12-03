package com.example.foodieworld.Fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodieworld.Adapter.BuyAgainAdapter
import com.example.foodieworld.R
import com.example.foodieworld.databinding.FragmentHistoryBinding
import com.example.foodieworld.model.orderDetails
import com.example.foodieworld.recentlyBuyItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderedFood: ArrayList<orderDetails> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        //retrieve the order data from the order history
        retrieveOrderHistory()
           //recent buy food items list
         binding.recentBuy.setOnClickListener {
             seeItemsRecentBuy()
         }
        binding.receviedBtn.setOnClickListener {
            updateOrderStatus()
        }
        return binding.root
    }

    private fun updateOrderStatus() {
       val ItemKey = listOfOrderedFood[0].itemPushKey
        val completeOrderReference = database.reference.child("CompletedOrder").child(ItemKey!!)
        completeOrderReference.child("paymentReceived").setValue(true)
    }

    private fun seeItemsRecentBuy() {
        listOfOrderedFood.firstOrNull()?.let {  recentBuy ->
            val intent = Intent(requireContext(),recentlyBuyItem::class.java)
            intent.putExtra("RecentBuyOrderItem",listOfOrderedFood)
            startActivity(intent)
        }
    }

    private fun retrieveOrderHistory() {
        binding.receviedBtn.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid ?: ""
        val buyItemReference: DatabaseReference =
            database.reference.child("user").child(userId).child("BuyHistory")
        val sortingList = buyItemReference.orderByChild("currentTime")

        sortingList.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               for(buySnapshot in snapshot.children){
                   val buyHistoryItem = buySnapshot.getValue(orderDetails::class.java)
                   buyHistoryItem?.let{
                       listOfOrderedFood.add(it)
                   }
               }
                listOfOrderedFood.reverse()
                if(listOfOrderedFood.isNotEmpty()){
                  //list o recently ordered food
                    setDataInRecentBuyItem()
                    //setup the recycler view for recent ordered food
                    setPreviousOrderedFoodRecycler()


                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun setDataInRecentBuyItem() {

        val recentOrderItem = listOfOrderedFood.firstOrNull()
        recentOrderItem?.let {
            with(binding){
                buyFoodName.text = it.foodName?.firstOrNull()?:""
                buyFoodPrice.text = it.foodPrices?.firstOrNull()?:""
                val image = it.foodImages?.firstOrNull()?:""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainImage)

              val IsOrderAccept = listOfOrderedFood[0].orderAccepted
                if(IsOrderAccept){
                    orderStatus.background.setTint(Color.GREEN)
                    receviedBtn.visibility = View.VISIBLE
                }
            }
        }

    }
// function for setup the recycler view for recent ordered food
    private fun setPreviousOrderedFoodRecycler() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainItemPrice = mutableListOf<String>()
        val buyAgainImages = mutableListOf<String>()
        for (i in 1 until listOfOrderedFood.size) {
            listOfOrderedFood[i].foodName?.firstOrNull()?.let {
                buyAgainFoodName.add(it)
                listOfOrderedFood[i].foodPrices?.firstOrNull()?.let {
                    buyAgainItemPrice.add(it)
                    listOfOrderedFood[i].foodImages?.firstOrNull()?.let {
                        buyAgainImages.add(it)
                    }
                }
            }
        }
        val rv = binding.RecyclerViewBuyAgain
        rv.layoutManager = LinearLayoutManager(requireContext())
         buyAgainAdapter = BuyAgainAdapter(buyAgainFoodName,buyAgainItemPrice,buyAgainImages,requireContext())
        rv.adapter = buyAgainAdapter

    }
}