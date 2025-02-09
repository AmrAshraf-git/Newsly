package com.ipro.newsly.core.datastore.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveAppEntry()
    suspend fun  readAppEntry(): Boolean
}