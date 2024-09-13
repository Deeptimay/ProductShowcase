package com.insane.productshowcase.domain.useCasesImpl

import com.insane.productshowcase.data.models.ApiResponse
import com.insane.productshowcase.domain.repositoryAbstraction.ProductRepository
import com.insane.productshowcase.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchHomePageData @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): NetworkResult<ApiResponse> {
        return productRepository.fetchHomePageData()
    }
}
