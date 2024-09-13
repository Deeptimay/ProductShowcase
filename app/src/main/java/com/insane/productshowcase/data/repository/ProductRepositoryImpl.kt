package com.insane.productshowcase.data.repository

import com.insane.productshowcase.data.models.Response
import com.insane.productshowcase.data.network.ProductApi
import com.insane.productshowcase.domain.repositoryAbstraction.ProductRepository
import com.insane.productshowcase.domain.util.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val baseRepository: BaseRepository,
    private val productApi: ProductApi,
) : ProductRepository {

    override suspend fun fetchHomePageData(): NetworkResult<List<Response>> {
        return baseRepository performApiCall {
            productApi.getHomePageData()
        }
    }
}
