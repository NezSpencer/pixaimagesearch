package com.nezspencer.pixaimagesearch.di

import android.content.Context
import com.nezspencer.pixaimagesearch.database.ImageDao
import com.nezspencer.pixaimagesearch.database.ImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesImageDatabase(@ApplicationContext appContext: Context): ImageDatabase =
        ImageDatabase.getDatabase(appContext)

    @Provides
    fun providesImageDao(database: ImageDatabase): ImageDao = database.imageDao()
}