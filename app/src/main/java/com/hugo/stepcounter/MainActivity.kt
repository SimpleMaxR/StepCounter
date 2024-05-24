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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.stepcounter.ui.theme.StepCounterTheme

class MainActivity : ComponentActivity() {
    private lateinit var sensorHandler: SensorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stepViewModel = StepViewModel() // 创建ViewModel，保存了 step、stepCount、moveTarget
        sensorHandler = SensorHandler(this, stepViewModel)
        enableEdgeToEdge()
        setContent {
            StepCounterTheme {
                Surface {
                    StepCounterScreen(viewModel =stepViewModel)
                    CircularProgress(viewModel = stepViewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("onResume", "onResume: ")
        sensorHandler.registerListener()    // 注册监听
    }

    override fun onPause() {
        super.onPause()
        sensorHandler.unregisterListener()  // 取消监听
    }
}

@Composable
fun StepCounterScreen(viewModel: StepViewModel = StepViewModel()){
    // 获取数据
    val stepState by viewModel.stepState.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp)) {
        Text(text = "当前走路 ${stepState.step} 步")
        Text(text = "Total ${stepState.stepCount} step today")
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    // 在 Preview 中使用 viewModel 需要添加 remember
    StepCounterScreen(remember {
        StepViewModel()
    })
    CircularProgress(viewModel = remember {
        StepViewModel()
    })
}