package com.fdev.yourdrive.di

import android.content.Context
import com.fdev.yourdrive.common.service.backup.BackupServiceHelperImpl
import com.fdev.yourdrive.domain.manager.BackupManager
import com.fdev.yourdrive.domain.repository.AppStateRepository
import com.fdev.yourdrive.domain.repository.BackupStatusRepository
import com.fdev.yourdrive.domain.service.BackupServiceHelper
import com.fdev.yourdrive.domain.usecase.backupManager.BackupUseCase
import com.fdev.yourdrive.domain.usecase.firstLoad.GetFirstLoadUseCase
import com.fdev.yourdrive.domain.usecase.backupManager.NetworkDriveConnectionUseCase
import com.fdev.yourdrive.domain.usecase.backupService.BackupServiceUseCases
import com.fdev.yourdrive.domain.usecase.backupService.StartBackupServiceUseCase
import com.fdev.yourdrive.domain.usecase.backupService.StopBackupServiceUseCase
import com.fdev.yourdrive.domain.usecase.backupStatus.GetBackupStatusUseCase
import com.fdev.yourdrive.domain.usecase.backupStatus.SetBackupStatusUseCase
import com.fdev.yourdrive.domain.usecase.firstLoad.UpdateFirstLoadUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun providesGetFirstLoadUseCase(appStateRepository: AppStateRepository): GetFirstLoadUseCase =
        GetFirstLoadUseCase(appStateRepository)

    @Singleton
    @Provides
    fun providesUpdateFirstLoadUseCase(appStateRepository: AppStateRepository): UpdateFirstLoadUseCase =
        UpdateFirstLoadUseCase(appStateRepository)

    @Singleton
    @Provides
    fun providesNetworkDriveConnectionUseCase(backupManager: BackupManager): NetworkDriveConnectionUseCase =
        NetworkDriveConnectionUseCase(backupManager)

    @Singleton
    @Provides
    fun providesBackupUseCase(backupManager: BackupManager): BackupUseCase =
        BackupUseCase(backupManager)

    @Singleton
    @Provides
    fun providesBackupServiceHelper(@ApplicationContext context: Context): BackupServiceHelper =
        BackupServiceHelperImpl(context)

    @Singleton
    @Provides
    fun providesStartBackupServiceUseCase(backupServiceHelper: BackupServiceHelper): StartBackupServiceUseCase =
        StartBackupServiceUseCase(backupServiceHelper)

    @Singleton
    @Provides
    fun providesStopBackupServiceUseCase(backupServiceHelper: BackupServiceHelper): StopBackupServiceUseCase =
        StopBackupServiceUseCase(backupServiceHelper)

    @Singleton
    @Provides
    fun providesBackupServiceUseCases(
        startBackupServiceUseCase: StartBackupServiceUseCase,
        stopBackupServiceUseCase: StopBackupServiceUseCase
    ): BackupServiceUseCases =
        BackupServiceUseCases(startBackupServiceUseCase, stopBackupServiceUseCase)

    @Singleton
    @Provides
    fun providesGetBackupStatusUseCase(backupStatusRepository: BackupStatusRepository): GetBackupStatusUseCase =
        GetBackupStatusUseCase(backupStatusRepository)

    @Singleton
    @Provides
    fun providesSetBackupStatusUseCase(backupStatusRepository: BackupStatusRepository): SetBackupStatusUseCase =
        SetBackupStatusUseCase(backupStatusRepository)
}