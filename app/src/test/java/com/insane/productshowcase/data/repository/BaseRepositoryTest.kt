package com.insane.productshowcase.data.repository

import com.insane.productshowcase.data.models.Item
import com.insane.productshowcase.data.models.Response
import com.insane.productshowcase.domain.util.ErrorTypes
import com.insane.productshowcase.domain.util.NetworkResult
import com.insane.productshowcase.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BaseRepositoryTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var baseRepository: BaseRepository

    @Before
    fun setUp() {
        baseRepository = BaseRepository()
    }

    @Test
    fun `performApiCall success`() = runBlocking {

        coEvery { response.isSuccessful } returns true
        coEvery { response.body() } returns bannerResponse

        val apiCall: suspend () -> retrofit2.Response<Response> = { response }
        val result = baseRepository.performApiCall(apiCall)

        assertTrue(result is NetworkResult.ApiSuccess)
        assertEquals(NetworkResult.ApiSuccess(bannerInstance), result)
    }

    @Test
    fun `performApiCall API error`() = runBlocking {
        val apiCall: suspend () -> retrofit2.Response<Response> = { errorResponse }
        val result = baseRepository.performApiCall(apiCall)

        assertEquals(
            NetworkResult.ApiError<Response>(
                ErrorTypes.CustomError(
                    errorCode,
                    errorMessage
                )
            ).errorData.message,
            (result as NetworkResult.ApiError<Response>).errorData.message
        )
    }

    @Test
    fun `performApiCall HTTP exception`() = runBlocking {

        val apiCall: suspend () -> retrofit2.Response<Response> = { throw customError }
        val result = baseRepository.performApiCall(apiCall)

        assertEquals(
            NetworkResult.ApiError<Response>(
                ErrorTypes.CustomError(
                    errorCode,
                    errorMessage
                )
            ).errorData.message,
            (result as NetworkResult.ApiError<Response>).errorData.message
        )
    }

    @Test
    fun `performApiCall general exception`() = runBlocking {

        val apiCall: suspend () -> retrofit2.Response<Response> = { throw exception }
        val result = baseRepository.performApiCall(apiCall)

        assertEquals(
            NetworkResult.ApiError<Response>(ErrorTypes.ExceptionError(exception)).errorData,
            (result as NetworkResult.ApiError<Response>).errorData
        )
    }

    companion object {
        private const val exceptionMessage = "Test Exception"
        val exception = ErrorTypes.ExceptionError(Exception(exceptionMessage))
        const val errorCode = 404
        const val errorMessage = "Not Found"
        val customError = ErrorTypes.CustomError(errorCode, errorMessage)
        val response = mockk<retrofit2.Response<Response>>()

        val errorResponse: retrofit2.Response<Response> = retrofit2.Response.error<Response>(
            errorCode,
            errorMessage.toResponseBody("text/plain".toMediaTypeOrNull())
        )

        val mockData = listOf(Item("https://images.pexels.com/photos/789812/pexels-photo-789812.jpeg", "Test Product"))
        val bannerInstance = Response.Banner(mockData)
        val bannerResponse = Response.Banner(mockData) as Response
    }
}
