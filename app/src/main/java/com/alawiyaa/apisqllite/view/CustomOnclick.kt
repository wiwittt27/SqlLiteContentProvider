package com.alawiyaa.apisqllite.view

import android.view.View

class CustomOnclick(private val position: Int, private val onItemCallback: OnItemClickCallback) :
    View.OnClickListener {
    interface OnItemClickCallback {
        fun onItemClicked(view: View,position: Int)
    }

    override fun onClick(v: View) {
        onItemCallback.onItemClicked(v,position)
    }
}