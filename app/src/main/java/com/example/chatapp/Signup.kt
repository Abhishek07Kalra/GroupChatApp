package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*
import java.time.LocalDateTime

class Signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        login.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        buttonSignUp.setOnClickListener {
            val email = editText5.text.toString()
            val password = editText4.text.toString()
            val name = editText6.text.toString()
            val city = editText8.text.toString()
            val state = editText7.text.toString()
            var i:Int = 0;
            var temp:String =""

            if (email == "")
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
            else if (password == "")
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            else {
                if(name == "" ) {
                    Toast.makeText(this, "Invalid name", Toast.LENGTH_SHORT).show()
                }
                else{
                    while(email[i] != '@')
                    {
                        temp+=email[i]
                        i+=1
                    }
                    progressBar2.visibility = View.VISIBLE
                    editText5.visibility = View.GONE
                    buttonSignUp.visibility = View.GONE
                    editText6.visibility = View.GONE
                    editText7.visibility = View.GONE
                    editText8.visibility = View.GONE
                    view2.visibility = View.GONE
                    login.visibility = View.GONE
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task2 ->
                        if (task2.isSuccessful) {
                            val email1 = FirebaseAuth.getInstance().currentUser!!.email
                            FirebaseDatabase.getInstance().reference.child("userDatabase").child(temp)
                                .push()
                                .setValue(
                                    Message1(email1!!, name , city , state )
                                )
                            startActivity(Intent(this, ChatActivity::class.java))
                            progressBar2.visibility = View.GONE
                            editText5.visibility = View.VISIBLE
                            editText6.visibility = View.VISIBLE
                            editText7.visibility = View.VISIBLE
                            editText8.visibility = View.VISIBLE
                            view2.visibility = View.VISIBLE
                            login.visibility = View.VISIBLE
                            buttonSignUp.visibility = View.VISIBLE
                            Toast.makeText(this, "SignUp Success", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            progressBar2.visibility = View.GONE
                            editText5.visibility = View.VISIBLE
                            editText6.visibility = View.VISIBLE
                            editText7.visibility = View.VISIBLE
                            editText8.visibility = View.VISIBLE
                            view2.visibility = View.VISIBLE
                            login.visibility = View.VISIBLE
                            buttonSignUp.visibility = View.VISIBLE
                            Toast.makeText(
                                this,
                                "Account already exist",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
                }


            }
        }

    }
}
class Message1(var email:String, var name:String , var city:String  , var state : String ) {
    constructor():this("" , "" , "" , "" )
}
