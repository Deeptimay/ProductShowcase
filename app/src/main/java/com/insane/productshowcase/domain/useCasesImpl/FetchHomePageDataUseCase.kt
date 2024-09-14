package com.insane.productshowcase.domain.useCasesImpl

import com.insane.productshowcase.data.models.Response
import com.insane.productshowcase.domain.repositoryAbstraction.ProductRepository
import com.insane.productshowcase.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchHomePageDataUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Response>> {
        return productRepository.fetchHomePageData()
    }
}