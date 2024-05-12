package com.example.mad4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.mad4.databinding.ActivityAddRoomsBinding

class addRooms : AppCompatActivity() {

    private lateinit var binding: ActivityAddRoomsBinding
    private lateinit var db : DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRoomsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= DBHelper(this)

        binding.saveButton.setOnClickListener {
            val name = binding.cName.text.toString()
            val  members = binding.memCount.text.toString().toInt()
            val rooms = binding.rType.text.toString()
            val  duration = binding.duration.text.toString()

            val book = booking(0, name, members, rooms, duration)
            db.addName(book)
            finish()
            Toast.makeText(this, "Booking Saved", Toast.LENGTH_SHORT).show()
        }
    }
}