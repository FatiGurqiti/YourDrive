package com.fdev.yourdrive.di

import android.content.Context
import com.fdev.yourdrive.common.Constant.DataStore.DATA_STORE
import com.fdev.yourdrive.data.local.dataStore.PreferenceDataStoreImpl
import com.fdev.yourdrive.domain.dataStore.PreferenceDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providesPreferencesDataStore(@ApplicationContext context: Context): PreferenceDataStore =
        PreferenceDataStoreImpl(context, DATA_STORE)
}