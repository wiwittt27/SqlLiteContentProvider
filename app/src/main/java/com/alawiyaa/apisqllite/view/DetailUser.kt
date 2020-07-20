package com.alawiyaa.apisqllite.view

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alawiyaa.apisqllite.R
import com.alawiyaa.apisqllite.data.local.database.DatabaseContract
import com.alawiyaa.apisqllite.data.local.database.UserHelper
import com.alawiyaa.apisqllite.data.remote.ApiService
import com.alawiyaa.apisqllite.data.remote.NetworkProvider
import com.alawiyaa.apisqllite.data.remote.response.ItemsItem
import com.alawiyaa.apisqllite.view.fragment.SectionPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUser : AppCompatActivity(), View.OnClickListener {

    private var isFavorite = false
    private var items: ItemsItem? = null
    private lateinit var adapter: GithubUserAdapter
    private lateinit var noteHelper: UserHelper

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        noteHelper = UserHelper.getInstance(applicationContext)
        noteHelper.open()

        items = intent.getParcelableExtra(EXTRA_TEXT)



        items?.login?.let { showDetailUser(it) }

        val selectionPage = SectionPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = selectionPage
        tabs.setupWithViewPager(viewPager)


        btnSave.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
        checkUser(items)

    }

    override fun onStart() {
        super.onStart()
        checkUser(items)
    }

    private fun checkUser(items:ItemsItem?) {
        val title = tvDUsername.text.toString()
        if (noteHelper.isUserExist(items?.login.toString())) {
            btnSave.visibility = View.GONE
            btnDelete.visibility = View.VISIBLE
        }else{
            btnSave.visibility = View.VISIBLE
            btnDelete.visibility = View.GONE
        }
    }

    fun showDetailUser(login: String) {
        val ds = NetworkProvider.providesHttpAdapter().create(
            ApiService::class.java
        )
        ds.findUserDetailByUsername(login).enqueue(object : Callback<ItemsItem> {
            override fun onFailure(call: Call<ItemsItem>, t: Throwable) {
                Toast.makeText(
                    this@DetailUser,
                    "Network Error cokk  ${t.message.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<ItemsItem>,
                response: Response<ItemsItem>
            ) {
                tvDUsername.text = response.body()?.login ?: "null"
                tvDName.text = response.body()?.name ?: "null"
                tvDCompany.text = response.body()?.company ?: "null"
                tvDLocation.text = response.body()?.location ?: "null"
                tvDRepo.text = response.body()?.publicRepos ?: "null"

                Glide.with(this@DetailUser).load(response.body()?.avatarUrl).into(imgDUser)


            }
        })

    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnDelete ->{
                noteHelper.deleteById(items?.id.toString())

                Toast.makeText(this@DetailUser, "berhasil menghapus data ${items?.login}", Toast.LENGTH_SHORT)
                    .show()
                finish()

            }
            R.id.btnSave->{
                val login = tvDUsername.text.toString()
                val name = tvDName.text.toString()
                val company = tvDCompany.text.toString()
                val location = tvDCompany.text.toString()
                val repo = tvDRepo.text.toString()

                val values = ContentValues()
                values.put(DatabaseContract.NoteColumns.LOGIN, login)
                values.put(DatabaseContract.NoteColumns.NAME, name)
                values.put(DatabaseContract.NoteColumns.COMPANY, company)
                values.put(DatabaseContract.NoteColumns.LOCATION,location)
                values.put(DatabaseContract.NoteColumns.REPOSITORY,repo)
                values.put(DatabaseContract.NoteColumns.FOLLOWERS, items?.followersUrl)
                values.put(DatabaseContract.NoteColumns.FOLLOWING, items?.followingUrl)
                values.put(DatabaseContract.NoteColumns.IMAGE, items?.avatarUrl)

                if (noteHelper.isUserExist(login)) {
                    Toast.makeText(
                        this@DetailUser,
                        "Duplikat",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val result = noteHelper.insert(values)
                    if (result > 0) {
                        items?.id = result.toInt()
                        val snack = Snackbar.make(consDetail,"Ditambahkan ke favorite",Snackbar.LENGTH_LONG)
                        snack.setAction("Lihat") {
                            val i = Intent(this, FavoriteActivity::class.java)
                            startActivity(i)
                        }
                        snack.show()
                        btnSave.visibility = View.GONE
                        btnDelete.visibility = View.VISIBLE


                    } else {
                        Toast.makeText(
                            this@DetailUser,
                            "Gagal",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }

            }

        }
           }
}
