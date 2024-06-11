package com.fdev.yourdrive.common.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty

class FlowUtil<T> {

    fun onErrorEmptyOrCompletion(
        request: Flow<T>,
        action: (cause: String?) -> Unit
    ): Flow<T> {

        val flow = request
            .catch {
                action(it.message)
            }
            .onEmpty {
                action(null)
            }
            .onCompletion {
                action(null)
            }

        return flow
    }
}