package com.example.fastpassapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "AccountDatabase"
        private val TABLE_CONTACTS = "AccountTable"
        private val TABLE_GENPASS = "GeneratedPasswordTable"
        private val KEY_EMAIL = "email"
        private val KEY_FNAME = "firstname"
        private val KEY_LNAME = "lastname"
        private val KEY_UNAME = "username"
        private val KEY_PASS = "password"
        private val KEY_GENPASS ="generated_password"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_EMAIL + " TEXT PRIMARY KEY,"
                + KEY_FNAME + " TEXT,"
                + KEY_LNAME + " TEXT,"
                + KEY_UNAME + " TEXT,"
                + KEY_PASS + " TEXT)")

        val CREATE_GENPASS_TABLE = ("CREATE TABLE " + TABLE_GENPASS + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_GENPASS + " TEXT,"
                + "FOREIGN KEY($KEY_EMAIL) REFERENCES $TABLE_CONTACTS($KEY_EMAIL))")

        db?.execSQL(CREATE_CONTACTS_TABLE)
        db?.execSQL(CREATE_GENPASS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_GENPASS")
        onCreate(db)
    }

    fun regAccount(user: User): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_EMAIL, user.email)
        contentValues.put(KEY_FNAME, user.fname)
        contentValues.put(KEY_LNAME, user.lname)
        contentValues.put(KEY_UNAME, user.uname)
        contentValues.put(KEY_PASS, user.pass)

        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }

    fun logAccount(username: String, password: String): Boolean {
        val columns = arrayOf(KEY_FNAME, KEY_LNAME, KEY_EMAIL)
        val db = this.readableDatabase
        val selection = "$KEY_UNAME = ? AND $KEY_PASS = ?"
        val selectionArgs = arrayOf(username, password)

        val cursor = db.query(TABLE_CONTACTS, columns, selection, selectionArgs, null, null, null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        return cursorCount > 0
    }

    @SuppressLint("Range")
    fun getUserDetails(uname: String): User? {
        val db = readableDatabase
        val projection = arrayOf(KEY_EMAIL, KEY_FNAME, KEY_LNAME)
        val selection = "$KEY_UNAME = ?"
        val selectionArgs = arrayOf(uname)

        val cursor: Cursor = db.query(
            TABLE_CONTACTS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
            val fname = cursor.getString(cursor.getColumnIndex(KEY_FNAME))
            val lname = cursor.getString(cursor.getColumnIndex(KEY_LNAME))

            cursor.close()
            return User(email, fname, lname, uname, "")
        }

        cursor.close()
        return null
    }

    fun saveGeneratedPassword(email: String, generatedPassword: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_EMAIL, email)
        contentValues.put(KEY_GENPASS, generatedPassword)

        val success = db.insert(TABLE_GENPASS, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getLast20GeneratedPasswords(email: String): List<String> {
        val db = readableDatabase
        val passwordList = mutableListOf<String>()

        val query = "SELECT $KEY_GENPASS FROM $TABLE_GENPASS WHERE $KEY_EMAIL = ? ORDER BY id DESC LIMIT 20"
        val selectionArgs = arrayOf(email)

        val cursor: Cursor = db.rawQuery(query, selectionArgs)

        while (cursor.moveToNext()) {
            val generatedPassword = cursor.getString(cursor.getColumnIndex(KEY_GENPASS))
            passwordList.add(generatedPassword)
        }

        cursor.close()
        db.close()

        return passwordList
    }
}