package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        button3.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this , MainActivity::class.java))
            Toast.makeText(this,"LogOut Success" , Toast.LENGTH_SHORT).show()
        }


        button2.setOnClickListener {
            val msg = editText3.text.toString()
            val email = FirebaseAuth.getInstance().currentUser!!.email
            if(msg == ""){
                Toast.makeText(this , "write something to send" , Toast.LENGTH_SHORT).show()
            }
            else {
                FirebaseDatabase.getInstance().reference.child("messages")
                    .push()
                    .setValue(
                        Message(email!!, msg)
                    )
                editText3.text.clear()
            }
        }
        val query = FirebaseDatabase.getInstance().reference.child("messages")

        val options =  FirebaseListOptions.Builder<Message>()
            .setLayout(android.R.layout.select_dialog_item)
            .setQuery(query , Message::class.java)
            .build()

        val adapter = object : FirebaseListAdapter<Message>(options) {
            override fun populateView(v: View, model: Message, position: Int) {
                (v as TextView).text = model.email  +"\n" + model.msg
            }
        }
        adapter.startListening()
        messages.adapter = adapter

    }
}

class Message(var email:String, var msg:String) {
    constructor():this("" , "")
}