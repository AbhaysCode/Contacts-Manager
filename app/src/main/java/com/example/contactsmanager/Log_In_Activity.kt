package com.example.contactsmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contactsmanager.databinding.ActivityLogInBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Log_In_Activity : AppCompatActivity() {
    lateinit var database:DatabaseReference
    lateinit var binding:ActivityLogInBinding
    companion object{
        val KEY1 = "com.example.contactsmanager.KEY.name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogin.setOnClickListener{
            val uniqueId = binding.etUniqueIdLogIn.text.toString()
            readData(uniqueId)
        }
    }

    private fun readData(uniqueId: String) {
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(uniqueId).get().addOnSuccessListener {
            if(it.exists()){
                val intent = Intent(this,MainActivity::class.java)
                val name = it.child("name").value.toString()
                val email = it.child("email").value.toString()
                intent.putExtra(KEY1,name)
                startActivity(intent)
            }else{
                Toast.makeText(this,"User Not Found, First Sign Up", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Registration Failed !! Server Issue",Toast.LENGTH_LONG).show()
        }
    }
}