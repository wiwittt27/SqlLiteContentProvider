package com.alawiyaa.apisqllite.helper

import android.database.Cursor
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract
import com.alawiyaa.apisqllite.data.remote.response.ItemsItem

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<ItemsItem> {
        val notesList = ArrayList<ItemsItem>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val login = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.LOGIN))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.NAME))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.LOCATION))
                val repository = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.REPOSITORY))
                val followers = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.FOLLOWING))
                val image = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.IMAGE))
                notesList.add(
                    ItemsItem(
                        id,
                        login,
                        name,
                        company,
                        location,
                        repository,
                        followers,
                        following,
                        image
                    )
                )
            }
        }
        return notesList
    }

    fun mapCursorToObject(notesCursor: Cursor?): ItemsItem {
        var note = ItemsItem()
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
            val login = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.LOGIN))
            val name = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.NAME))
            val company = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.COMPANY))
            val location = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.LOCATION))
            val repository = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.REPOSITORY))
            val followers = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.FOLLOWERS))
            val following = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.FOLLOWING))
            val image = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.IMAGE))
            note =
                ItemsItem(id, login, name, company, location,repository, followers, following, image)
        }
        return note
    }
}