package com.alawiyaa.apisqllite.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class GithubResponse(

	@SerializedName("total_count")
	var totalCount: Int? = null,

	@SerializedName("items")
	var items: List<ItemsItem>
)
@Parcelize
data class ItemsItem(

	var id:Int=0,
	@SerializedName("login")
	var login: String? = null,

	@SerializedName("name")
	var name: String? = null,

	@SerializedName("company")
	var company: String? = null,


	@SerializedName("location")
	var location: String? = null,

	@SerializedName("public_repos")
	var publicRepos: String? = null,

	@SerializedName("following_url")
	var followingUrl: String? = null,


	@SerializedName("followers_url")
	var followersUrl: String? = null,

	@SerializedName("avatar_url")
	var avatarUrl: String? = null



):Parcelable
