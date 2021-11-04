package com.nezspencer.pixaimagesearch.data

class FakeAppCache : AppCache {
    private var ttlCache = 0L
    private var isValid = true

    override suspend fun saveTtl(lastFetchedInMillis: Long) {
        ttlCache = lastFetchedInMillis + 5
        isValid = true
    }

    override suspend fun isStaleData(): Boolean = !isValid

    fun setDataValidity(isValid: Boolean) {
        this.isValid = isValid
    }
}