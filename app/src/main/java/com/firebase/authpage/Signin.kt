package com.firebase.authpage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.authpage.databinding.SigninActivityBinding
import com.google.firebase.auth.FirebaseAuth

class Signin: AppCompatActivity(){
    private lateinit var binding: SigninActivityBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SigninActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()

        //If this click view is clicked, it will go to the signup page
        binding.textView6.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        //If credentials and password is correct, this part takes you to the main page
        binding.btnSingIn.setOnClickListener {
            val email = binding.mail2.text.toString()
            val pass = binding.pass2.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
