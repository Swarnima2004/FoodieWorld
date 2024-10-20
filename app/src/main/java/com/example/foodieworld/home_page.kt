package com.example.foodieworld

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodieworld.Fragments.NotificationBottom
import com.example.foodieworld.databinding.ActivityHomePageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class home_page : AppCompatActivity() {
    private val binding: ActivityHomePageBinding by lazy {
        ActivityHomePageBinding.inflate(layoutInflater)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
////            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        var navController = findNavController(R.id.fragmentContainerView)
        var bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomnav.setupWithNavController(navController)

        binding.imageView3.setOnClickListener {
            val bottomSheetNotification = NotificationBottom()
            bottomSheetNotification.show(supportFragmentManager, "test")
        }
    }
}