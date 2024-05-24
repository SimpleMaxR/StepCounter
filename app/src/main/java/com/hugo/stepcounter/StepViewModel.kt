package com.hugo.stepcounter

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class StepUiState(
    val step: Int = 0,
    val stepCount: Int = 0,
    val moveTarget: Int = 20
)
class StepViewModel: ViewModel() {

    private val _stepState = MutableStateFlow(StepUiState())
    val stepState: StateFlow<StepUiState> = _stepState.asStateFlow()



    fun updateStep() {
        _stepState.value = _stepState.value.copy(step = _stepState.value.step + 1)
        Log.i("ViewModel", "updateStep to ${_stepState.value.step}")
    }

    fun updateStepCount(stepCount: Int) {
        _stepState.value = _stepState.value.copy(stepCount = stepCount)
    }

}