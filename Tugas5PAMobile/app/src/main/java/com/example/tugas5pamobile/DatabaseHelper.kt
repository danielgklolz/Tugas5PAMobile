package com.example.tugas5pamobile

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "countries.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_COUNTRY = "countries"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_COUNTRY ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT)")
        db.execSQL(createTable)

        // Menambahkan data awal
        val countries = listOf("Indonesia", "Malaysia", "Singapore", "Thailand", "Philippines", "Vietnam", "Brunei", "Laos", "Cambodia", "Myanmar")
        for (country in countries) {
            addCountry(country, db)
        }
    }

    private fun addCountry(name: String, db: SQLiteDatabase) {
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
        }
        db.insert(TABLE_COUNTRY, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COUNTRY")
        onCreate(db)
    }

    fun getAllCountries(): List<Country> {
        val countries = mutableListOf<Country>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_COUNTRY", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                countries.add(Country(id, name))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return countries
    }

    fun addCountry(name: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
        }
        db.insert(TABLE_COUNTRY, null, values)
        db.close()
    }
}
