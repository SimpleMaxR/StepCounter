package com.hugo.stepcounter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class StepUiState(
    val step: Int = 0,
    val stepCount: Int = 0
)
class StepViewModel: ViewModel() {

    private val _stepState = MutableStateFlow(StepUiState())
    val stepState: StateFlow<StepUiState> = _stepState.asStateFlow()

    fun updateStep(step: Int) {
        _stepState.value = _stepState.value.copy(step = step)
    }

    fun updateStepCount(stepCount: Int) {
        _stepState.value = _stepState.value.copy()
    }

}