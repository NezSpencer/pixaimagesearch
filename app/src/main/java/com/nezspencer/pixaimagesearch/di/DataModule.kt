package com.nezspencer.pixaimagesearch.di

import com.nezspencer.pixaimagesearch.data.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun bindsRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindsAppCache(appCacheImpl: AppCacheImpl): AppCache

    @Binds
    abstract fun bindsImageRepository(repositoryImpl: ImageRepositoryImpl): ImageRepository

}