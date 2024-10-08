package com.insane.productshowcase.di

import com.insane.productshowcase.data.repository.ProductRepositoryImpl
import com.insane.productshowcase.domain.repositoryAbstraction.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesProductRepository(impl: ProductRepositoryImpl): ProductRepository
}
