package com.fdev.yourdrive.ui.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdev.yourdrive.ui.screen.base.ViewEffect
import com.fdev.yourdrive.ui.screen.base.ViewEvent
import com.fdev.yourdrive.ui.screen.base.ViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : ViewState, Event : ViewEvent, Effect : ViewEffect> :
    ViewModel() {

    abstract val initialState: State

    val effectTag: String
        get() = "${this::class.simpleName}Effect"

    private var eventJob: Job? = null

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State>
        get() = _state.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event>
        get() = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    abstract fun onEvent(event: Event)

    private fun setOnEvent() {
        viewModelScope.launch {
            event.collect {
                onEvent(it)
            }
        }
    }

    init {
        setOnEvent()
    }

    fun setEvent(event: Event) {
        if (eventJob?.isActive != true) {
            eventJob = viewModelScope.launch {
                _event.emit(event)
            }
        }
    }

    fun setState(proceed: State.() -> State) {
        val state = state.value.proceed()
        _state.value = state
    }

    fun Effect.setEffect() {
        viewModelScope.launch {
            _effect.send(this@setEffect)
        }
    }
}