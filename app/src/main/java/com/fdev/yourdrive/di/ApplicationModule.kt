package com.fdev.yourdrive.di

import android.content.Context
import com.fdev.yourdrive.domain.manager.BackupManager
import com.fdev.yourdrive.common.manager.backup.BackupManagerImpl
import com.fdev.yourdrive.common.manager.firebase.CrashlyticsManagerImpl
import com.fdev.yourdrive.domain.manager.CrashlyticsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun providesCrashlyticsManager(): CrashlyticsManager =
        CrashlyticsManagerImpl()

    @Singleton
    @Provides
    fun providesBackupManager(
        @ApplicationContext context: Context,
        crashlyticsManager: CrashlyticsManager
    ): BackupManager =
        BackupManagerImpl(context, crashlyticsManager)
}