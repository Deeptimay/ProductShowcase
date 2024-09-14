package com.insane.productshowcase.di.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class OfflineInterceptor @Inject constructor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val maxStale = 60 * 60 * 24 * 2 // 2 days

        // If offline, use cache up to a certain limit (e.g., 2 days)
        if (!isNetworkAvailable()) {
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(1, TimeUnit.DAYS)
                .build()
            request = request.newBuilder()
                .cacheControl(cacheControl)
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }

        return chain.proceed(request)
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