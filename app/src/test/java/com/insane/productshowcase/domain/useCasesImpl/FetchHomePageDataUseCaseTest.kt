package com.insane.productshowcase.domain.useCasesImpl

import com.insane.productshowcase.data.models.Item
import com.insane.productshowcase.data.models.Response
import com.insane.productshowcase.domain.repositoryAbstraction.ProductRepository
import com.insane.productshowcase.domain.util.ErrorTypes
import com.insane.productshowcase.domain.util.NetworkResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchHomePageDataUseCaseTest {

    private lateinit var useCase: FetchHomePageDataUseCase
    private val productRepository: ProductRepository = mockk()

    @Before
    fun setUp() {
        useCase = FetchHomePageDataUseCase(productRepository)
    }

    @Test
    fun `invoke should return success when repository returns ApiSuccess`() = runTest {
        // Arrange: Mock the repository to return ApiSuccess
        coEvery { productRepository.fetchHomePageData() } returns NetworkResult.ApiSuccess(response)

        // Act: Call the use case
        val result = useCase()

        // Assert: Verify that the result is ApiSuccess and contains the expected data
        assertTrue(result is NetworkResult.ApiSuccess)
        assertEquals(responseNew, (result as NetworkResult.ApiSuccess).data)

        // Verify that the repository method was called
        coVerify(exactly = 1) { productRepository.fetchHomePageData() }
    }

    @Test
    fun `invoke should return error when repository returns ApiError`() = runTest {
        // Arrange: Mock the repository to return ApiError
        coEvery { productRepository.fetchHomePageData() } returns NetworkResult.ApiError(errorData)

        // Act: Call the use case
        val result = useCase()

        // Assert: Verify that the result is ApiError and contains the expected error message
        assertTrue(result is NetworkResult.ApiError)
        assertEquals(errorData, (result as NetworkResult.ApiError).errorData)

        // Verify that the repository method was called
        coVerify(exactly = 1) { productRepository.fetchHomePageData() }
    }

    companion object {
        val mockData = listOf(Item("https://images.pexels.com/photos/789812/pexels-photo-789812.jpeg", "Test Product"))
        val bannerInstance = Response.Banner(mockData)
        val responseNew = listOf(bannerInstance)
        val bannerResponse = Response.Banner(mockData) as Response
        val response = listOf(bannerResponse)
        val errorData = ErrorTypes.NoConnectivityException(404, "No internet connection")
    }
}
