package com.example.mad4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mad4.databinding.ActivityAddRoomsBinding
import com.example.mad4.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var booksAdapter: BooksAdapter
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)
        booksAdapter = BooksAdapter(db.getAllBooks(), this)

        binding.booksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.booksRecyclerView.adapter = booksAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, addRooms::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        booksAdapter.refreshData(db.getAllBooks())
    }

    fun deleteBook(bookId: Int) {
        db.deleteBook(bookId)
        val books = db.getAllBooks()
    }
}
