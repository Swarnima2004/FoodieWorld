package com.example.foodieworld

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodieworld.Fragments.confirmBottomSheet
import com.example.foodieworld.databinding.ActivityBuyAndPayBinding
import com.example.foodieworld.databinding.BuyAgainItemBinding
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
    private lateinit var Price :String
    private lateinit var  Amount :String
    private lateinit var foodItemName:  Array<String>
    private lateinit var FoodItemPrice: Array<String>
    private lateinit var FoodItemImage :Array<String>
    private lateinit var FoodItemDescription: Array<String>
    private lateinit var FoodItemIngredient :  Array<String>
    private lateinit var FoodItemQuantities : Array<Int>
    private lateinit var userID :String

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
        binding.placeorderbtn.setOnClickListener {
            val bottomSheetDialog = confirmBottomSheet()
            bottomSheetDialog.show(supportFragmentManager,"test")
        }
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