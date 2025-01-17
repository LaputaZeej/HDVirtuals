package com.yunext.virtuals.ui

import com.yunext.virtuals.ui.data.PlatformSerializable
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

interface StateHolder<S, E> {
    val state: StateFlow<S>
}

@Serializable
sealed interface Effect:PlatformSerializable {
    @Serializable
    object Idle : Effect
    @Serializable
    object Processing : Effect
    @Serializable
    data class Fail(
        @Contextual
        val e: Throwable) : Effect
    @Serializable
    object Success : Effect
}

val Effect.processing :Boolean
    get() = this == Effect.Processing

abstract class AbstractStateHolder<S, E>(
    defaultState: S,
    coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.Default + SupervisorJob() + CoroutineName(
            "AbstractStateHolder"
        )
    ),
) : StateHolder<S, E>, CoroutineScope by coroutineScope {
    private val _state: MutableStateFlow<S> = MutableStateFlow(defaultState)
    override val state: StateFlow<S> = _state.asStateFlow()
    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: Flow<Effect> = _effect.asSharedFlow()

    protected fun state(changed: S.() -> S) {
        _state.update(changed)
    }

    protected fun state(state: S) {
        _state.value = state
    }

    private fun effect(effect: Effect) {
        launch {
            _effect.emit(effect)
        }
    }

    private fun effect(effect: () -> Effect) {
        effect(effect())
    }

    protected fun request(block: suspend () -> S) {
        launch {
            try {
                effect(Effect.Processing)
                val r = block()
                state(r)
                effect(Effect.Success)
            } catch (e: Throwable) {
                e.printStackTrace()
                effect(Effect.Fail(e))
            } finally {
                effect {
                    Effect.Idle
                }
            }
        }
    }

}