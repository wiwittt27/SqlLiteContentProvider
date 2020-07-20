package com.alawiyaa.apisqllite.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alawiyaa.apisqllite.R
import com.alawiyaa.apisqllite.data.remote.response.ItemsItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class GithubUserAdapter(val listUsers: ArrayList<ItemsItem>) :
    RecyclerView.Adapter<GithubUserAdapter.UserAdapter>() {

    private lateinit var onItemClickCallback: UserListener
    fun setOnItemClickCallback(onItemClickCallback: UserListener) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(noteList: ArrayList<ItemsItem>) {
        listUsers.clear()
        listUsers.addAll(noteList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GithubUserAdapter.UserAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)

        return UserAdapter(view)
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun onBindViewHolder(holder: GithubUserAdapter.UserAdapter, position: Int) {
        val item = listUsers[position]
        val ctx = holder.itemView.context
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClickCallback(item)

        }

    }

    inner class UserAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: ItemsItem) {
            with(itemView) {
                tvUsername.text = user.login
                Glide.with(context).load(user.avatarUrl).into(imgUser)

            }

        }
    }

    interface UserListener {
        fun onItemClickCallback(item: ItemsItem)

    }

}
