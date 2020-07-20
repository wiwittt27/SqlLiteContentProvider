package com.alawiyaa.apisqllite.data.local.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract.NoteColumns.Companion.COMPANY
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract.NoteColumns.Companion.FOLLOWERS
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract.NoteColumns.Companion.FOLLOWING
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract.NoteColumns.Companion.IMAGE
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract.NoteColumns.Companion.LOCATION
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract.NoteColumns.Companion.LOGIN
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract.NoteColumns.Companion.NAME
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract.NoteColumns.Companion.REPOSITORY
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract.NoteColumns.Companion._ID
import com.alawiyaa.apisqllite.data.remote.response.ItemsItem
import java.sql.SQLException

class UserHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: UserHelper? = null
        private lateinit var database: SQLiteDatabase
        private lateinit  var dataBaseHelper: DatabaseHelper

        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
            }
    }
    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }
    fun isUserExist(login: String): Boolean {

        val cursor = database.query(
            TABLE_NAME,
            arrayOf(_ID, LOGIN, NAME, COMPANY, LOCATION, REPOSITORY, FOLLOWERS, FOLLOWING, IMAGE),
            "$LOGIN=?",
            arrayOf(login),  //Where clause
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst() && cursor.count > 0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true
        }

        cursor.close()
        //if email does not exist return false
        return false
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

    fun getAllNotes(): ArrayList<ItemsItem> {
        val arrayList = ArrayList<ItemsItem>()
        val cursor = database.query(DATABASE_TABLE, null, null, null, null, null,
            "$_ID ASC", null)
        cursor.moveToFirst()
        var note: ItemsItem
        if (cursor.count > 0) {
            do {
                note = ItemsItem()
                note.id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID))
                note.login = cursor.getString(cursor.getColumnIndexOrThrow(LOGIN))
                note.name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                note.company = cursor.getString(cursor.getColumnIndexOrThrow(COMPANY))
                note.location = cursor.getString(cursor.getColumnIndexOrThrow(LOCATION))
                note.publicRepos = cursor.getString(cursor.getColumnIndexOrThrow(REPOSITORY))
                note.followersUrl = cursor.getString(cursor.getColumnIndexOrThrow(FOLLOWERS))
                note.followingUrl = cursor.getString(cursor.getColumnIndexOrThrow(FOLLOWING))
                note.avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE))

                arrayList.add(note)
                cursor.moveToNext()

            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }
}