package com.fdev.yourdrive.di

import com.fdev.yourdrive.domain.repository.AppStateRepository
import com.fdev.yourdrive.domain.usecase.GetFirstLoadUseCase
import com.fdev.yourdrive.domain.usecase.SetFirstLoadUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun providesSetFirstLoadUseCase(appStateRepository: AppStateRepository): SetFirstLoadUseCase =
        SetFirstLoadUseCase(appStateRepository)
}