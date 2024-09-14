package com.insane.productshowcase.di.network

import android.content.Context
import com.insane.productshowcase.data.models.Response
import com.insane.productshowcase.data.network.ProductApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, onlineInterceptor: OnlineInterceptor,
        offlineInterceptor: OfflineInterceptor, @ApplicationContext application: Context
    ): OkHttpClient {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        // Cache directory in the app's cache folder
        val cacheDir = File(application.cacheDir, "http_cache")
        val cache = Cache(cacheDir, cacheSize)

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(offlineInterceptor) // Offline support
            .addNetworkInterceptor(onlineInterceptor) // Online support
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(polymorphicAdapter)
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi, @Named("baseUrl") baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideTrendingApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String = "https://jsonkeeper.com/"


    private val polymorphicAdapter =
        PolymorphicJsonAdapterFactory.of(Response::class.java, "sectionType")
            .withSubtype(Response.Banner::class.java, "banner")
            .withSubtype(Response.HorizontalFreeScroll::class.java, "horizontalFreeScroll")
            .withSubtype(Response.SplitBanner::class.java, "splitBanner")


    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    })
}
