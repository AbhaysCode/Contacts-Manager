package com.example.contactsmanager


import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.contactsmanager.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var database:DatabaseReference
    lateinit var dialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)
        dialog.setContentView(R.layout.customised_alert)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.bg))

        val name = intent.getStringExtra(Log_In_Activity.KEY1)
        if (name != null) {
            binding.tvName.text = "Welcome, ${name.capitalize()}"
        }
        var btnDone = dialog.findViewById<Button>(R.id.btnDone)

        btnDone.setOnClickListener{
            Toast.makeText(this,"Contact Added..",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
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
                        dialog.show()
                        Toast.makeText(this,"Contact Successfully Registered", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this,"Registration Failed !! Server Issue",Toast.LENGTH_LONG).show()
                    }
                }
            }

        }


    }
}