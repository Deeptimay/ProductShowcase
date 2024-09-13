package com.insane.productshowcase.presentation.utils

import com.insane.productshowcase.domain.util.ErrorTypes


sealed interface UiState {
    data object Loading : UiState
    data class Success<T>(val content: T) : UiState
    data class Error(val throwable: ErrorTypes? = null) : UiState
}
