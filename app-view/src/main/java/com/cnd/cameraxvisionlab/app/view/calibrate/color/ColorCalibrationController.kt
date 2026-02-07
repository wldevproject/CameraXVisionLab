package com.cnd.cameraxvisionlab.app.view.calibrate.color

import com.cnd.cameraxvisionlab.core.vision.color.*

class ColorCalibrationController {

    var current: ColorCalibration? = null
        private set

    fun updateFromHsv(
        category: ColorCategory,
        hsv: FloatArray
    ) {
        val h = hsv[0]
        val s = hsv[1]
        val v = hsv[2]

        current = ColorCalibration(
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
}