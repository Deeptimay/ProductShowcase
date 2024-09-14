package com.insane.productshowcase.di.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.insane.productshowcase.domain.util.ErrorTypes
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OnlineInterceptor @Inject constructor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable()) {
            throw ErrorTypes.NoConnectivityException(404, "No internet connection")
        }
        val response = chain.proceed(chain.request())
        val cacheControl = response.header("Cache-Control")
        return response.newBuilder()
            .header("Cache-Control", cacheControl ?: "public, max-age=60") // Cache for 1 minute
            .build()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

