package com.alawiyaa.apisqllite.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(
	
	@SerializedName("following_url")
	val followingUrl: String? = null,

	@SerializedName("login")
	val login: String? = null,

	@SerializedName("company")
	val company: String? = null,

	@SerializedName("starred_url")
	val starredUrl: String? = null,

	@SerializedName("followers_url")
	val followersUrl: String? = null,

	@SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("location")
	val location: String? = null

)
