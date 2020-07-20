package com.alawiyaa.apisqllite.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alawiyaa.apisqllite.R
import com.alawiyaa.apisqllite.data.remote.ApiService
import com.alawiyaa.apisqllite.data.remote.NetworkProvider
import com.alawiyaa.apisqllite.data.remote.response.GithubResponse
import com.alawiyaa.apisqllite.data.remote.response.ItemsItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCari.setOnClickListener {
            val login = edtSearch.text.toString()
            if (login == "") return@setOnClickListener
            searchUser(login)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemFavorite->{
                val i = Intent(this,FavoriteActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun searchUser(login: String) {
        val ds = NetworkProvider.providesHttpAdapter().create(
            ApiService::class.java)
        ds.findUserByUsername(login).enqueue(object : Callback<GithubResponse> {
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Network Error cokk  ${t.message.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                val list = response.body()?.items
                val adapter = GithubUserAdapter(list as ArrayList<ItemsItem>)

                rvUser.adapter = adapter
                adapter.setOnItemClickCallback(object :GithubUserAdapter.UserListener{
                    override fun onItemClickCallback(item: ItemsItem) {
                        val i = Intent(this@MainActivity,DetailUser::class.java)
                        i.putExtra(DetailUser.EXTRA_TEXT,item)
                        startActivity(i)
                    }
                })
                setRecycle()
            }
        })

    }

    fun setRecycle() {
        rvUser.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}
