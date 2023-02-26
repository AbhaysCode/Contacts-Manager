package com.example.contactsmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contactsmanager.databinding.ActivitySignUpBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Sign_Up_Activity : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener{
            var name = binding.etName.text.toString()
            var email = binding.etEmail.text.toString()
            var uniqueId = binding.etUniqueId.text.toString()
            val user = User(name,email,uniqueId)
            database = FirebaseDatabase.getInstance().getReference("Users")
            database.child(uniqueId).get().addOnSuccessListener {
                if(it.exists()){
                    Toast.makeText(this,"Id Not Unique Try Again !",Toast.LENGTH_SHORT).show()
                }else{
                    database.child(uniqueId).setValue(user).addOnSuccessListener {
                        binding.etName.text?.clear()
                        binding.etEmail.text?.clear()
                        binding.etUniqueId.text?.clear()
                        Toast.makeText(this,"User Successfully Registered", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this,"Registration Failed !! Server Issue",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.btnAlreadyReg.setOnClickListener{
            val intent = Intent(this,Log_In_Activity::class.java)
            startActivity(intent)
        }
    }
}