package com.fdev.yourdrive.common.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty

class FlowUtil<T> {

    fun onErrorEmptyOrCompletion(
        request: Flow<T>,
        action: suspend (cause: String) -> Unit,
        onEmptyMessage: String = String.Empty,
        onCompletedMessage: String = String.Empty
    ): Flow<T> {

        val flow = request
            .catch {
                action(it.message ?: String.Empty)
            }
            .onEmpty {
                action(onEmptyMessage)
            }
            .onCompletion {
                action(onCompletedMessage)
            }

        return flow
    }
}