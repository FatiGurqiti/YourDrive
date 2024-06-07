package com.fdev.yourdrive.domain.usecase

import com.fdev.yourdrive.domain.repository.AppStateRepository

class UpdateFirstLoadUseCase(private val appStateRepository: AppStateRepository) {
    suspend operator fun invoke() {
        appStateRepository.updateFirstLoad()
    }
}