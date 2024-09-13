package com.insane.productshowcase.domain.repositoryAbstraction

import com.insane.productshowcase.data.models.Response
import com.insane.productshowcase.domain.util.NetworkResult


interface ProductRepository {
    suspend fun fetchHomePageData(): NetworkResult<List<Response>>
}
