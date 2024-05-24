package com.hugo.stepcounter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

/**
 *  This class represents a sensor handler that listens to step counter and step detector sensors.
 *
 *  @param context The application context.
 *  @param viewModel The view model to update step information.
 *  @property step The number of steps taken since the last reset.
 *  @property stepCount The total number of steps taken by the user.
 *  @author Hugo
 *  @since 1.0
 */
class SensorHandler(context: Context, private val viewModel: StepViewModel): SensorEventListener {
    private val mSensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager  // 传感器管理器
    private lateinit var mStepCounter: Sensor   // 计步传感器
    private lateinit var mStepDetector: Sensor  // 检测传感器
    var step by mutableIntStateOf(0)   // 步数
    var stepCount by mutableIntStateOf(0)  // 总共步数

    init {
        // 获取传感器
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!!
        } else {
            Log.e("onResume", "onResume: 没有找到传感器 stepCounter")
        }
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!!
        } else {
            Log.e("onResume", "onResume: 没有找到传感器 stepDetector")
        }


    }

    /**
     * 为 step counter 和 step detector 注册监听器
     */
    fun registerListener(){
        // 注册监听器
        mSensorManager.registerListener(
            this,
            mStepCounter,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        mSensorManager.registerListener(
            this,
            mStepDetector,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun unregisterListener(){
        mSensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int){
        Log.i("Sensor", "onAccuracyChanged: $accuracy")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        Log.i("onSensorChanged", "onSensorChanged: ")
        // 针对不同的传感器类型做不同的处理
        when (event?.sensor?.type) {
            Sensor.TYPE_STEP_DETECTOR -> {
                if (event.values[0] == 1.0f) {
                    step++
                    viewModel.updateStep()
                }
            }
            Sensor.TYPE_STEP_COUNTER -> {
                stepCount = event.values[0].toInt()
                viewModel.updateStepCount(event.values[0].toInt())
            }
        }
    }

}