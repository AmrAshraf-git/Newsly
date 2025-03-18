package com.ipro.newsly.core.datastore.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveAppEntry()
    fun  readAppEntry():Flow<Boolean>

    /*suspend fun  readAppEntry(): Boolean*/
}