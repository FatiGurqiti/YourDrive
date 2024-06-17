package com.fdev.yourdrive.domain.manager

interface CrashlyticsManager {
    fun logNonFatalException(e: Throwable)
}