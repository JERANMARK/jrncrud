package com.example.mad4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mad4.databinding.ActivityUpdateBookBinding

class update_book : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBookBinding
    private lateinit var db : DBHelper
    private var bookId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        bookId = intent.getIntExtra("book_id",-1)
        if (bookId == -1){
            finish()
            return
        }

        val book = db.getBookByID(bookId)
        binding.updatecName.setText(book.name)
        binding.updatememCount.setText(book.members.toString())
        binding.updaterType.setText(book.rooms)
        binding.updateduration.setText(book.duration)


        binding.updateSaveButton.setOnClickListener {
            val newName = binding.updatecName.text.toString()
            val newMember = binding.updatememCount.text.toString().toInt()
            val newRooms = binding.updaterType.text.toString()
            val newDuration = binding.updateduration.text.toString()

            val updatedBook = booking(bookId, newName, newMember,newRooms,newDuration)
            db.updateBook(updatedBook)
            finish()
            Toast.makeText(this,"Booking Changes Saved", Toast.LENGTH_SHORT).show()
        }



    }
}