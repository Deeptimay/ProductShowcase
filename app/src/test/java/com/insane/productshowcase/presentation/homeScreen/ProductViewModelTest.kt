package com.insane.productshowcase.presentation.homeScreen

import app.cash.turbine.test
import com.insane.productshowcase.data.models.Item
import com.insane.productshowcase.data.models.Response
import com.insane.productshowcase.domain.useCasesImpl.FetchHomePageDataUseCase
import com.insane.productshowcase.domain.util.ErrorTypes
import com.insane.productshowcase.domain.util.NetworkResult
import com.insane.productshowcase.presentation.utils.UiState
import com.insane.productshowcase.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ProductViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    private lateinit var viewModel: ProductViewModel
    private val fetchHomePageDataUseCase: FetchHomePageDataUseCase = mockk()

    @Before
    fun setUp() {
        // Initialize ViewModel
        viewModel = ProductViewModel(fetchHomePageDataUseCase)
    }

    @Test
    fun `getProductList emits Loading followed by Success when ApiSuccess is returned`() = runTest {
        // Arrange: Mock the use case to return ApiSuccess
        coEvery { fetchHomePageDataUseCase() } returns NetworkResult.ApiSuccess(response)

        // Act: Start observing the flow
        viewModel.productListFlow.test {
            // Assert: Check that the first value emitted is UiState.Loading
            assertEquals(UiState.Loading, awaitItem())

            // Trigger the API call
            viewModel.getProductList()

            // Assert: Check that UiState.Success is emitted with the expected data
            assertEquals(UiState.Success(responseNew), awaitItem())
            cancelAndIgnoreRemainingEvents() // Clean up after test
        }
    }

    @Test
    fun `getProductList emits Loading followed by Error when ApiError is returned`() = runTest {
        // Arrange: Mock the use case to return ApiError
        coEvery { fetchHomePageDataUseCase() } returns NetworkResult.ApiError(errorData)

        // Act: Start observing the flow
        viewModel.productListFlow.test {
            // Assert: Check that the first value emitted is UiState.Loading
            assertEquals(UiState.Loading, awaitItem())

            // Trigger the API call
            viewModel.getProductList()

            // Assert: Check that UiState.Error is emitted with the expected error data
            assertEquals(UiState.Error(errorData), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getProductList is called during ViewModel initialization`() = runTest {
        // Arrange: Mock the use case to return ApiSuccess
        coEvery { fetchHomePageDataUseCase() } returns NetworkResult.ApiSuccess(response)

        // Act: Start observing the flow
        viewModel.productListFlow.test {
            // Assert: Check that the first value emitted is UiState.Loading
            assertEquals(UiState.Loading, awaitItem())

            // Assert: Check that UiState.Success is emitted during ViewModel init
            assertEquals(UiState.Success(responseNew), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
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
