package com.fdev.yourdrive.common.manager.firebase

import com.fdev.yourdrive.domain.manager.CrashlyticsManager
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashlyticsManagerImpl : CrashlyticsManager {

    override fun logNonFatalException(e: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(e)
    }
}