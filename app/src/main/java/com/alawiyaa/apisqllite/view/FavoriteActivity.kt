package com.alawiyaa.apisqllite.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alawiyaa.apisqllite.R
import com.alawiyaa.apisqllite.data.local.database.UserHelper
import com.alawiyaa.apisqllite.data.remote.response.ItemsItem
import com.alawiyaa.apisqllite.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    var adapterUser: GithubUserAdapter? = null
    private var listUser = ArrayList<ItemsItem>()
    private lateinit var noteHelper: UserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        noteHelper = UserHelper.getInstance(applicationContext)
        noteHelper.open()
        setRecycle(listUser)
        loadAsync()
    }

    private fun setRecycle(listUser: ArrayList<ItemsItem>) {
        rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapterUser = GithubUserAdapter(listUser)
            adapter = adapterUser

        }
        adapterUser?.setOnItemClickCallback(object :GithubUserAdapter.UserListener{
            override fun onItemClickCallback(item: ItemsItem) {
                val i = Intent(this@FavoriteActivity,DetailUser::class.java)
                i.putExtra(DetailUser.EXTRA_TEXT,item)
                startActivity(i)
                Toast.makeText(this@FavoriteActivity,"ini ${item.login}",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progressbar.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
               MappingHelper.mapCursorToArrayList(cursor)



            }
            progressbar.visibility = View.INVISIBLE
            val notes = deferredNotes.await()
            if (notes.size > 0) {
                adapterUser?.setData(notes)
                adapterUser = GithubUserAdapter(notes)

                setRecycle(notes)

            } else {

                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        noteHelper.close()
    }

    private fun showSnackbarMessage(s: String) {
        Snackbar.make(rvFavorite, s, Snackbar.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        noteHelper.open()
        loadAsync()
    }
}
