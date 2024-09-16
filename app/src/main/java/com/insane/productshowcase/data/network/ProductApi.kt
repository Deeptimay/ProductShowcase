package com.insane.productshowcase.data.network


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ProductApi {
    @Headers("Accept: application/json")
    @GET("/b/B011")
    suspend fun getHomePageData(): Response<List<com.insane.productshowcase.data.models.Response>>
}
