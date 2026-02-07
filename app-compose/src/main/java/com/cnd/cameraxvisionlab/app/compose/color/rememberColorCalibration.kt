package com.cnd.cameraxvisionlab.app.compose.color

import androidx.compose.runtime.*
import com.cnd.cameraxvisionlab.core.vision.color.*

@Composable
fun rememberColorCalibration(): Pair<ColorCalibration?, (FloatArray, ColorCategory) -> Unit> {
    var calibration by remember { mutableStateOf<ColorCalibration?>(null) }

    val update: (FloatArray, ColorCategory) -> Unit = { hsv, category ->
        val h = hsv[0]
        val s = hsv[1]
        val v = hsv[2]

        calibration = ColorCalibration(
            category = category,
            range = ColorRange(
                hMin = (h - 10).coerceAtLeast(0f),
                hMax = (h + 10).coerceAtMost(360f),
                sMin = (s - 0.2f).coerceAtLeast(0f),
                sMax = (s + 0.2f).coerceAtMost(1f),
                vMin = (v - 0.2f).coerceAtLeast(0f),
                vMax = (v + 0.2f).coerceAtMost(1f),
            )
        )
    }

    return calibration to update
}