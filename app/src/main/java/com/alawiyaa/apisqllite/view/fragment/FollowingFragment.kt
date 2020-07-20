package com.alawiyaa.apisqllite.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.alawiyaa.apisqllite.R
import com.alawiyaa.apisqllite.data.remote.ApiService
import com.alawiyaa.apisqllite.data.remote.NetworkProvider
import com.alawiyaa.apisqllite.data.remote.response.ItemsItem
import com.alawiyaa.apisqllite.view.DetailUser
import com.alawiyaa.apisqllite.view.GithubUserAdapter
import kotlinx.android.synthetic.main.fragment_following.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class FollowingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = activity?.intent?.getParcelableExtra(DetailUser.EXTRA_TEXT) as ItemsItem

       data.login?.let { showFollowing(it) }
    }

    private fun showFollowing(login: String) {
        val ds = NetworkProvider.providesHttpAdapter().create(
            ApiService::class.java)
        ds.getFollowing(login).enqueue(object :Callback<List<ItemsItem>>{
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Toast.makeText(
                    activity,
                    "Network Error cokk  ${t.message.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
               val result = response.body()
                val adapter = GithubUserAdapter(result as ArrayList<ItemsItem>)

                rvFollowing.adapter = adapter
                adapter.setOnItemClickCallback(object :GithubUserAdapter.UserListener{
                    override fun onItemClickCallback(item: ItemsItem) {
                        Toast.makeText(activity,"${item.login}",Toast.LENGTH_SHORT).show()
                    }
                })
                setRecycle()
            }
        })
    }
    fun setRecycle() {
        rvFollowing.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}
