package com.alawiyaa.apisqllite.data.remote

import com.alawiyaa.apisqllite.BuildConfig
import com.alawiyaa.apisqllite.data.remote.response.GithubResponse
import com.alawiyaa.apisqllite.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun findUserByUsername(
        @Query("q") username: String
    ): Call<GithubResponse>


    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun findUserDetailByUsername(
        @Path("username") username: String
    ): Call<ItemsItem>


    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getFollower(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

}