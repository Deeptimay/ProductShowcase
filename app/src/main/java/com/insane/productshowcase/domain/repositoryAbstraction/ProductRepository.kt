package com.insane.productshowcase.domain.repositoryAbstraction

import com.insane.productshowcase.domain.util.NetworkResult
import com.insane.productshowcase.data.models.ApiResponse

interface ProductRepository {
    suspend fun fetchHomePageData(): NetworkResult<ApiResponse>
}
