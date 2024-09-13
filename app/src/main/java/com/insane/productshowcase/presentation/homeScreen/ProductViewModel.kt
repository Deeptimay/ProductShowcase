package com.insane.productshowcase.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insane.productshowcase.domain.useCasesImpl.FetchHomePageData
import com.insane.productshowcase.domain.util.NetworkResult
import com.insane.productshowcase.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val fetchHomePageData: FetchHomePageData
) : ViewModel() {

    private val _productListFlow = MutableStateFlow<UiState>(UiState.Loading)
    val productListFlow: StateFlow<UiState> = _productListFlow.asStateFlow()

    init {
        getProductList()
    }

    internal fun getProductList() {
        viewModelScope.launch {
            val response = fetchHomePageData()
            _productListFlow.update {
                when (response) {
                    is NetworkResult.ApiSuccess -> UiState.Success(response.data)
                    is NetworkResult.ApiError -> UiState.Error(response.errorData)
                }
            }
        }
    }
}
