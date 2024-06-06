package com.fdev.yourdrive.domain.repository

interface AppStateRepository {
    suspend fun isFirstLoad(): Boolean
    suspend fun updateFirstLoad()
}