package com.alawiyaa.apisqllite.data.local.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.alawiyaa.apisqllite"
    const val SCHEME = "content"

    internal class NoteColumns : BaseColumns {
        companion object{
            const val TABLE_NAME = "tb_user"
            const val _ID = "_id"
            const val LOGIN = "login"
            const val NAME = "name"
            const val COMPANY = "company"
            const val LOCATION = "location"
            const val REPOSITORY = "repository"
            const val FOLLOWERS = "followers_url"
            const val FOLLOWING = "following_url"
            const val IMAGE = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}