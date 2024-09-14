package com.insane.productshowcase.data.repository

import com.insane.productshowcase.data.models.Item
import com.insane.productshowcase.data.models.Response
import com.insane.productshowcase.data.network.ProductApi
import com.insane.productshowcase.domain.util.ErrorTypes
import com.insane.productshowcase.domain.util.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductRepositoryImplTest {

    private lateinit var productRepository: ProductRepositoryImpl
    private val baseRepository: BaseRepository = mockk()
    private val productApi: ProductApi = mockk()

    @Before
    fun setUp() {
        productRepository = ProductRepositoryImpl(baseRepository, productApi)
    }

    @Test
    fun `fetchHomePageData should return ApiSuccess when API call is successful`() = runTest {
        // Arrange: Mock the API and BaseRepository to return ApiSuccess
        coEvery { productApi.getHomePageData() } returns mockk()
        coEvery { baseRepository.performApiCall<List<Response>>(any()) } returns networkResult

        // Act: Call the repository function
        val result = productRepository.fetchHomePageData()

        // Assert: Verify that the result is ApiSuccess and the correct data is returned
        assertTrue(result is NetworkResult.ApiSuccess)
        assertEquals(listOfBannerInstance, (result as NetworkResult.ApiSuccess).data)
    }

    @Test
    fun `fetchHomePageData should return ApiError when API call fails`() = runTest {
        // Arrange: Mock the API and BaseRepository to return ApiError

        coEvery { productApi.getHomePageData() } returns mockk()
        coEvery { baseRepository.performApiCall<List<Response>>(any()) } returns networkError

        // Act: Call the repository function
        val result = productRepository.fetchHomePageData()

        // Assert: Verify that the result is ApiError and contains the correct error information
        assertTrue(result is NetworkResult.ApiError)
        assertTrue((result as NetworkResult.ApiError).errorData is ErrorTypes.CustomError)
        assertEquals(errorCode, (result.errorData as ErrorTypes.CustomError).code)
        assertEquals(errorMessage, (result.errorData as ErrorTypes.CustomError).internalMessage)
    }

    @Test
    fun `fetchHomePageData should propagate ApiError when API returns null body`() = runTest {
        // Arrange: Mock the API and BaseRepository to return null body

        coEvery { productApi.getHomePageData() } returns mockk()
        coEvery { baseRepository.performApiCall<List<Response>>(any()) } returns networkError

        // Act: Call the repository function
        val result = productRepository.fetchHomePageData()

        // Assert: Verify that the result is ApiError with appropriate error
        assertTrue(result is NetworkResult.ApiError)
        assertTrue((result as NetworkResult.ApiError).errorData is ErrorTypes.CustomError)
    }

    companion object {
        const val errorCode = 404
        const val errorMessage = "Not Found"
        val customError = ErrorTypes.CustomError(errorCode, errorMessage)

        val mockData = listOf(Item("https://images.pexels.com/photos/789812/pexels-photo-789812.jpeg", "Test Product"))
        val bannerInstance = Response.Banner(mockData)
        val listOfBannerInstance = listOf(bannerInstance)
        val bannerResponse = Response.Banner(mockData) as Response
        val networkResult = NetworkResult.ApiSuccess<List<Response>>(listOf(bannerResponse))
        val networkError = NetworkResult.ApiError<List<Response>>(customError)
    }
}
