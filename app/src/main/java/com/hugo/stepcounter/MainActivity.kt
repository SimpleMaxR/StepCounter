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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hugo.stepcounter.ui.theme.StepCounterTheme

class MainActivity : ComponentActivity() {
    private lateinit var sensorHandler: SensorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorHandler = SensorHandler(this)
        enableEdgeToEdge()
        setContent {
            StepCounterTheme {
                Surface {
                    StepCounterScreen(step = sensorHandler.step, stepCount = sensorHandler.stepCount)
                    CircularProgress(viewModel = StepViewModel())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("onResume", "onResume: ")
        sensorHandler.registerListener()
    }

    override fun onPause() {
        super.onPause()
        sensorHandler.unregisterListener()
    }
}

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
    StepCounterScreen(step = 12, stepCount = 1000)
    CircularProgress(viewModel = StepViewModel())
}