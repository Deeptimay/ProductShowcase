package com.insane.productshowcase.data.network

import com.insane.productshowcase.data.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET("b/5BEJ")
    suspend fun getHomePageData(): Response<ApiResponse>
}
