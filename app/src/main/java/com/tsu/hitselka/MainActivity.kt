package com.tsu.hitselka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tsu.hitselka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val database = Firebase.database("https://hitselka-default-rtdb.asia-southeast1.firebasedatabase.app")
        val myRef = database.getReference("message")

        binding.saveButton.setOnClickListener {
            val text = binding.editText.text.toString()
            myRef.setValue(text)
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }

        onChangeListener(myRef)
    }

    private fun onChangeListener(dbReference: DatabaseReference) {
        dbReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.textView.append("\n")
                binding.textView.append(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}