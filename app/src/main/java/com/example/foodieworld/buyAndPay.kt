
package com.example.foodieworld

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodieworld.Fragments.CartFragment
import com.example.foodieworld.Fragments.confirmBottomSheet
import com.example.foodieworld.databinding.ActivityBuyAndPayBinding
import com.example.foodieworld.databinding.BuyAgainItemBinding
import com.example.foodieworld.model.orderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class buyAndPay : AppCompatActivity() {
    private lateinit var binding:ActivityBuyAndPayBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRefrence: DatabaseReference
    private lateinit var Name :String
    private lateinit var Address :String
    private lateinit var Phone :String
    private lateinit var  Amount :String
    private lateinit var foodItemName:  ArrayList<String>
    private lateinit var foodItemPrice: ArrayList<String>
    private lateinit var foodItemImage :ArrayList<String>
    private lateinit var foodItemDescription: ArrayList<String>
    private lateinit var foodItemIngredient :  ArrayList<String>
    private lateinit var foodItemQuantities : ArrayList<Int>
    private lateinit var userId :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBuyAndPayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //initialising firebase
        auth = FirebaseAuth.getInstance()
        databaseRefrence = FirebaseDatabase.getInstance().getReference()

        //set data
        setUserData()

        //get user details from firebase
        val intent = intent
        foodItemName = intent.getStringArrayListExtra("FoodItemName") as ArrayList<String>
        foodItemPrice = intent.getStringArrayListExtra("FoodItemPrice") as ArrayList<String>
        foodItemImage = intent.getStringArrayListExtra("FoodItemImage") as ArrayList<String>
        foodItemDescription = intent.getStringArrayListExtra("FoodItemDescription") as ArrayList<String>
        foodItemIngredient = intent.getStringArrayListExtra("FoodItemIngredients") as ArrayList<String>
        foodItemQuantities = intent.getIntegerArrayListExtra("FoodItemQualities") as ArrayList<Int>

        Amount = CalculateTotalAmount().toString() + "$"
        binding.price.isEnabled = false
        binding.price.setText(Amount)

         binding.backBtn.setOnClickListener {
             finish()
         }

        binding.placeorderbtn.setOnClickListener {
            //extracting data from textView
            Name = binding.name.text.toString().trim()
            Address = binding.address.text.toString().trim()
            Phone = binding.phone.text.toString().trim()

            if(Name.isBlank() && Address.isBlank() && Phone.isBlank()){
                Toast.makeText(this, "Please Enter All The Details😊", Toast.LENGTH_SHORT).show()
            }
            else{
                placeOrder()
            }

        }
    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid?:""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseRefrence.child("Order Details").push().key
        val orderDetails = orderDetails(userId,Name,foodItemName,foodItemPrice,foodItemImage,foodItemQuantities,Address,Amount,Phone,time,itemPushKey,false,false)
         val orderReference = databaseRefrence.child("Order Details").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = confirmBottomSheet()
            removeItemFromCard()
            addOrderToHistory(orderDetails)
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed To Order 😔", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addOrderToHistory(orderDetails: orderDetails) {
         databaseRefrence.child("user").child(userId).child("BuyHistory").child(orderDetails.itemPushKey!!)
             .setValue(orderDetails).addOnSuccessListener {

         }
    }

    private fun removeItemFromCard() {
        val cartItemReference = databaseRefrence.child("user").child(userId).child("Cartitems")
        cartItemReference.removeValue()
    }

    private fun CalculateTotalAmount(): Int {
     var totalAmount = 0
        for( i in 0 until  foodItemPrice.size){
            var price = foodItemPrice[i]
            val lastchar = price.last()
            val priceIntValue = if (lastchar == '$'){
                price.dropLast(1).toInt()
            }
            else{
                price.toInt()
            }
            var quantity = foodItemQuantities[i]
            totalAmount += priceIntValue*quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if( user != null){
            val userID = user.uid
            val userReference = databaseRefrence.child("user").child(userID)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val names = snapshot.child("name").getValue(String::class.java)?:""
                        val addresses = snapshot.child("address").getValue(String::class.java)?:""
                        val phones = snapshot.child("phone").getValue(String::class.java)?:""

                        binding.apply{
                            name.setText(names)
                            address.setText(addresses)
                            phone.setText(phones)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        }
    }
}