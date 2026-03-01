package com.firebase.authpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.authpage.databinding.SignupActivityBinding
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {

    private lateinit var binding: SignupActivityBinding
    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Initializing firebase instance
        firebaseAuth = FirebaseAuth.getInstance()

        //if the user already has an account and clicks this,it redirects to Signin page
        binding.textView3.setOnClickListener {
            val intent = Intent(this, Signin::class.java)
            startActivity(intent)
        }

        //Once the user enters email and passwords correctly,it redirects to sign in page
        binding.btnSignUp.setOnClickListener {

            //Getting email and password from the user
            val email = binding.mail.text.toString()
            val pass = binding.pass.text.toString()
            val confirmPass = binding.confPass.text.toString()

            //The if statements check if the fields are empty or not
            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                            //this part starts Signin activity if the credentials are correct
                            val intent = Intent(this, Signin::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{ //this part is for if the passwords do not match
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }else{ //This ensure that the fields are not empty
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
