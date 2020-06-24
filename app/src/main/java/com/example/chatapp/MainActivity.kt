package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(FirebaseAuth.getInstance().currentUser != null){

            startActivity(Intent(this , ChatActivity::class.java))
        }
        button.setOnClickListener {
            val email = editText.text.toString()
            val password = editText2.text.toString()
            if (email == "")
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
            else if (password == "")
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            else {
                progressBar.visibility = View.VISIBLE
                editText.visibility = View.GONE
                editText2.visibility = View.GONE
                button.visibility = View.GONE
                view1.visibility = View.GONE
                signup.visibility = View.GONE
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task1 ->
                        if (task1.isSuccessful) {
                            startActivity(Intent(this, ChatActivity::class.java))
                            progressBar.visibility = View.GONE
                            editText.visibility = View.VISIBLE
                            editText2.visibility = View.VISIBLE
                            button.visibility = View.VISIBLE
                            view1.visibility = View.VISIBLE
                            signup.visibility = View.VISIBLE
                            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            progressBar.visibility = View.GONE
                            editText.visibility = View.VISIBLE
                            editText2.visibility = View.VISIBLE
                            button.visibility = View.VISIBLE
                            view1.visibility = View.VISIBLE
                            signup.visibility = View.VISIBLE
                            Toast.makeText(
                                this,
                                "invalid credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


            }

        }
            signup.setOnClickListener {
                startActivity(Intent(this , Signup::class.java))
            }

    }
}