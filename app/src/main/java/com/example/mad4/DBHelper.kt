package com.example.mad4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):SQLiteOpenHelper(context,AKSHAYA_REST,null,DATABASE_VERSION) {

    companion object {
        private const val AKSHAYA_REST = "AKSHAYA_REST.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "roomlist"
        const val C_ID = "id"
        const val C_NAME = "name"
        const val C_MEM = "members"
        const val C_RM = "rooms"
        const val C_DU = "duration"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME( $C_ID INTEGER PRIMARY KEY,$C_NAME  TEXT,$C_MEM  INTEGER,$C_RM TEXT,$C_DU TEXT)"
        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun addName(book: booking) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(C_NAME, book.name)
            put(C_MEM, book.members)
            put(C_RM, book.rooms)
            put(C_DU, book.duration)

        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllBooks(): List<booking> {
        val bookList= mutableListOf<booking>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor=db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(C_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
            val members = cursor.getInt(cursor.getColumnIndexOrThrow(C_MEM))
            val rooms = cursor.getString(cursor.getColumnIndexOrThrow(C_RM))
            val duration = cursor.getString(cursor.getColumnIndexOrThrow(C_DU))

            val book = booking (id,name,members,rooms, duration)
            bookList.add(book)
        }
        cursor.close()
        db.close()
        return bookList
    }

    fun updateBook(book: booking){
        val db = writableDatabase
        val values=ContentValues().apply {
            put(C_NAME,book.name)
            put(C_MEM,book.members)
            put(C_RM,book.rooms)
            put(C_DU,book.duration)

        }
        val whereClause = "$C_ID = ?"
        val whereArgs = arrayOf(book.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    fun getBookByID(bookId: Int): booking{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $C_ID = $bookId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(C_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
        val members = cursor.getInt(cursor.getColumnIndexOrThrow(C_MEM))
        val rooms = cursor.getString(cursor.getColumnIndexOrThrow(C_RM))
        val duration = cursor.getString(cursor.getColumnIndexOrThrow(C_DU))

        cursor.close()
        db.close()
        return booking(id,name, members,rooms,duration)

    }

    fun deleteBook(bookId: Int){
        val db = writableDatabase
        val whereClause = "$C_ID = ?"
        val whereArgs = arrayOf(bookId.toString())
        db.delete(TABLE_NAME, whereClause,whereArgs)
        db.close()
    }

}