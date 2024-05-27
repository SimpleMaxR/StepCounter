package com.hugo.stepcounter

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class StepUiState(
    val step: Int = 0, // 打开 App 以来步数
    val stepCount: Int = 0, // 手机重启以来步数
    val moveTarget: Int = 20    // 步数目标
)
class StepViewModel: ViewModel() {

    // 将内部数据封装成 StateFlow 暴露出去
    private val _stepState = MutableStateFlow(StepUiState())
    val stepState: StateFlow<StepUiState> = _stepState.asStateFlow()


    fun updateMoveTarget(moveTarget: Int) {
        _stepState.value = _stepState.value.copy(moveTarget = moveTarget)
    }

    fun updateStep() {
        _stepState.value = _stepState.value.copy(step = _stepState.value.step + 1)
        Log.i("ViewModel", "updateStep to ${_stepState.value.step}")
    }

    fun resetStep() {
        _stepState.value = _stepState.value.copy(step = 0)
    }

    fun updateStepCount(stepCount: Int) {
        _stepState.value = _stepState.value.copy(stepCount = stepCount)
    }

}