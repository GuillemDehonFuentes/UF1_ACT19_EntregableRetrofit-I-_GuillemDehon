package com.example.uf1_act19_entregableretrofit_guillemdehon

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/posts")
    fun getAllPosts(): Call<List<Post>>

    @GET("/posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<Post>
}
