package com.example.mad4

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView


class BooksAdapter(private var books: List<booking>, private val context: Context) : RecyclerView.Adapter<BooksAdapter.BookViewHolder> (){

    private val db: DBHelper= DBHelper(context)

    class BookViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val memTextView: TextView = itemView.findViewById(R.id.memTextView)
        val roomTextView: TextView = itemView.findViewById(R.id.roomTextView)
        val duTextView: TextView = itemView.findViewById(R.id.duTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.nameTextView.text = book.name
        holder.memTextView.text = book.members.toString()
        holder.roomTextView.text = book.rooms
        holder.duTextView.text = book.duration.toString()

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, update_book::class.java).apply {
                putExtra("book_id", book.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            showDeleteConfirmationDialog(book)
        }
    }

    private fun showDeleteConfirmationDialog(book: booking) {
        AlertDialog.Builder(context)
            .setMessage("Do you want to delete your booking?")
            .setPositiveButton("Yes") { _, _ ->
                (context as MainActivity).deleteBook(book.id) // Call deleteNote from MainActivity
                Toast.makeText(context, "Your booking Deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun refreshData(newBooks: List<booking>){
        books = newBooks
        notifyDataSetChanged()
    }

}