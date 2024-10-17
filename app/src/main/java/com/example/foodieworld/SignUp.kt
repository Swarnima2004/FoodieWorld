package com.example.foodieworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodieworld.databinding.ActivitySignUpBinding
import com.example.foodieworld.model.userModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var googleSignInClient: GoogleSignInClient


    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        // initialise the firebase authentication
        auth = Firebase.auth
        //initialise the database
        database = Firebase.database.reference
        //initialise google signin
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.createAccountbtn.setOnClickListener {
            username = binding.Name.text.toString()
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            } else {
                createUser(email, password)
            }
        }
        binding.already.setOnClickListener {
            val intent = Intent(this, login_page::class.java)
            startActivity(intent)
        }
        binding.google.setOnClickListener {
            val SignIntent = googleSignInClient.signInIntent
            launcher.launch(SignIntent)
        }
    }

    //launcher for google signin
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "SignIn Successful 😊", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, home_page::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "SignIn Failed 😔", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            } else {
                Toast.makeText(this, "SignIn Failed 😔", Toast.LENGTH_SHORT).show()
            }
        }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                saveUserInfo()
                startActivity(Intent(this, login_page::class.java))
                finish()
            } else {
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "createAccount: Failure", task.exception)
            }
        }

    }

    private fun saveUserInfo() {
        //retrieve data from user field
        username = binding.Name.text.toString()
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = userModel(username, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        //save the data in firebase
        database.child("user").child(userId).setValue(user)
    }
}