package com.hugo.stepcounter

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset

val CIRCULAR_TIMER_RADIUS = 500f


@Composable
fun CircularProgress(
    modifier: Modifier = Modifier,
    viewModel: StepViewModel
) {
    val stepState = viewModel.stepState.collectAsState()
    val step = stepState.value.step
    val moveTarget = stepState.value.moveTarget
    //  _progress 圆环进度
    val _progress = if (moveTarget == 0) {
        0f
    } else {
        maxOf(0.0001f, (step.toFloat() / moveTarget.toFloat()) * 360f)
    }
    // 动画值
    val progress by animateFloatAsState(_progress, animationSpec = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessLow
    ))

    val color:Color = when {
            progress < 180f -> Color.Red
            progress < 360f -> Color.Yellow
            else -> Color.Green
    }


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
                sweepAngle = progress,
                useCenter = false,
                color = color,
                style = Stroke(width = 70f, cap = StrokeCap.Round)
            )
        }

    }
}