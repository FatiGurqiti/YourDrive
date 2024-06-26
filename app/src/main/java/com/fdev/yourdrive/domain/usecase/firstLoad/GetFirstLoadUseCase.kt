package com.fdev.yourdrive.domain.usecase.firstLoad

import com.fdev.yourdrive.domain.repository.AppStateRepository

class GetFirstLoadUseCase(private val appStateRepository: AppStateRepository) {
    suspend operator fun invoke() = appStateRepository.isFirstLoad()
}