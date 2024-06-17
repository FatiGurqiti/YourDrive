package com.fdev.yourdrive.di

import com.fdev.yourdrive.data.local.repository.AppStateRepositoryImpl
import com.fdev.yourdrive.data.local.repository.BackupStatusRepositoryImpl
import com.fdev.yourdrive.domain.dataStore.PreferenceDataStore
import com.fdev.yourdrive.domain.repository.AppStateRepository
import com.fdev.yourdrive.domain.repository.BackupStatusRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesAppStateRepository(dataStore: PreferenceDataStore): AppStateRepository =
        AppStateRepositoryImpl(dataStore)

    @Singleton
    @Provides
    fun providesBackupStatusRepository(dataStore: PreferenceDataStore): BackupStatusRepository =
        BackupStatusRepositoryImpl(dataStore)
}