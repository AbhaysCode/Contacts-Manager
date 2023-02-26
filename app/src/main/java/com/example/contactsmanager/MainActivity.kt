package com.example.contactsmanager


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contactsmanager.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra(Log_In_Activity.KEY1)
        if (name != null) {
            binding.tvName.text = "Welcome, ${name.capitalize()}"
        }
        binding.btnAddContact.setOnClickListener{
            val cName = binding.etContactName.text.toString()
            val cEmail = binding.etContactEmail.text.toString()
            val phoneNum = binding.etContactPhoneNumber.text.toString()
            val contact = Contact(cName,cEmail,phoneNum)
            database = FirebaseDatabase.getInstance().getReference("Contacts")
            database.child(phoneNum).get().addOnSuccessListener {
                if(it.exists()){
                    Toast.makeText(this,"Phone Number Already Registered !",Toast.LENGTH_SHORT).show()
                }else{
                    database.child(phoneNum).setValue(contact).addOnSuccessListener {
                        binding.etContactName.text?.clear()
                        binding.etContactEmail.text?.clear()
                        binding.etContactPhoneNumber.text?.clear()
                        Toast.makeText(this,"Contact Successfully Registered", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this,"Registration Failed !! Server Issue",Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }
}