package com.hugo.stepcounter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.stepcounter.ui.theme.StepCounterTheme

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var mSensorManager: SensorManager  // 传感器管理器
    private lateinit var mStepCounter: Sensor   // 计步传感器
    private lateinit var mStepDetector: Sensor  // 检测传感器
    private var step by mutableStateOf(0)   // 步数
    private var stepCount by mutableStateOf(0)  // 总共步数

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        enableEdgeToEdge()
        setContent {
            StepCounterTheme {
                Surface {
                    StepCounterScreen(step = step, stepCount = stepCount)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("onResume", "onResume: ")

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

    override fun onPause() {
        super.onPause()
        // 取消监听
        mSensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.i("onAccuracyChanged", "onAccuracyChanged: $accuracy")
    }

    // onSensorChanged 传感器数据变化
    override fun onSensorChanged(event: SensorEvent?) {
        Log.i("onSensorChanged", "onSensorChanged: ")
        // 针对不同的传感器类型做不同的处理
        when (event?.sensor?.type) {
            Sensor.TYPE_STEP_DETECTOR -> {
                if (event.values[0] == 1.0f) {
                    step++
                }
            }
            Sensor.TYPE_STEP_COUNTER -> {
                stepCount = event.values[0].toInt()
            }
        }
    }
}

//class CircularTransitionData(
//    progress: State<Float>,
//    color: State<Color>
//) {
//    val progress by progress
//    val color by color
//}

//@Composable
//fun CircularProgress(
//    transitionData: CircularTransitionData,
//    modifier: Modifier = Modifier
//) {
//    Canvas(
//        modifier = modifier
//            .fillMaxWidth()
//            .requiredHeight(200.dp)
//    ) {
//
//    }
//}

@Composable
fun StepCounterScreen(step: Int, stepCount: Int){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp)) {
        Text(text = "当前走路 $step 步")
        Text(text = "Total $stepCount step today")
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StepCounterTheme {
        StepCounterScreen(step = 0, stepCount = 0)
    }
}