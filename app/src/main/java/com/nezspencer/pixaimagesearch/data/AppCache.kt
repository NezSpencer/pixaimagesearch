package com.nezspencer.pixaimagesearch.data

import android.content.Context
import android.os.SystemClock
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

interface AppCache {
    suspend fun saveTtl(lastFetchedInMillis: Long)

    suspend fun isStaleData(): Boolean
}

@Singleton
class AppCacheImpl @Inject constructor(@ApplicationContext private val appContext: Context) : AppCache {
    private val sharedPreferences by lazy {
        appContext.getSharedPreferences(
            "${appContext.packageName}_preferences",
            Context.MODE_PRIVATE
        )
    }

    override suspend fun saveTtl(lastFetchedInMillis: Long) {
        val ttl = lastFetchedInMillis + java.util.concurrent.TimeUnit.DAYS.toMillis(2)
        sharedPreferences.edit { putLong(KEY_TTL, ttl) }
    }

    override suspend fun isStaleData(): Boolean {
        val ttl = sharedPreferences.getLong(KEY_TTL, 0L)
        return ttl < SystemClock.elapsedRealtime()
    }

    companion object {
        private const val KEY_TTL = "key_ttl"
    }
}