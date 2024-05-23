package com.hugo.stepcounter

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.unit.dp

val CIRCULAR_TIMER_RADIUS = 300f

class CircularTransitionData(
    progress: State<Float>,
    color: State<Color>
) {
    val progress by progress
    val color by color
}

@Composable
fun CircularProgress(
    transitionData: CircularTransitionData = CircularTransitionData(
        progress = mutableStateOf(100f),
        color = mutableStateOf(Color.Red)
    ),
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        inset(size.width / 2 - CIRCULAR_TIMER_RADIUS, size.height / 2 - CIRCULAR_TIMER_RADIUS) {
            drawCircle(
                color = Color.Gray,
                radius = CIRCULAR_TIMER_RADIUS,
                center = center,
                style = Stroke(width = 70f, cap = StrokeCap.Round)
            )

            drawArc(
                startAngle = 270f, // 270 is 0 degree
                sweepAngle = transitionData.progress,
                useCenter = false,
                color = transitionData.color,
                style = Stroke(width = 70f, cap = StrokeCap.Round)
            )
        }

    }
}