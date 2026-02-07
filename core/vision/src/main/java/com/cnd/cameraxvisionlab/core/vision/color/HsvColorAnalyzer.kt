package com.cnd.cameraxvisionlab.core.vision.color

import android.graphics.ImageFormat
import androidx.camera.core.ImageProxy
import com.cnd.cameraxvisionlab.core.analyzer.FrameData
import com.cnd.cameraxvisionlab.core.analyzer.VisionResult
import com.cnd.cameraxvisionlab.core.analyzer.VisionType
import com.cnd.cameraxvisionlab.core.camerax.ImageAnalyzerBase
import java.nio.ByteBuffer

class HsvColorAnalyzer(
    private val calibration: ColorCalibration?,
    private val onDetected: (ColorResult, FloatArray) -> Unit
) : ImageAnalyzerBase() {

    override fun analyzeFrame(
        image: ImageProxy,
        frameData: FrameData
    ): VisionResult? {

        if (image.format != ImageFormat.YUV_420_888) return null

        val rgb = sampleCenterRgb(image) ?: return null
        val hsv = rgbToHsv(rgb.first, rgb.second, rgb.third)

        val result = if (calibration == null) {
            ColorResult(
                category = ColorCategory.UNKNOWN,
                confidence = 0.1f,
                hsv = hsv
            )
        } else {
            val range = calibration.range

            val match =
                hsv[0] in range.hMin..range.hMax &&
                        hsv[1] in range.sMin..range.sMax &&
                        hsv[2] in range.vMin..range.vMax

            ColorResult(
                category = if (match) calibration.category else ColorCategory.UNKNOWN,
                confidence = if (match) 0.85f else 0.2f,
                hsv = hsv
            )
        }

        // 🔔 kirim ke UI / app-layer
        onDetected(result, hsv)

        return VisionResult(
            type = VisionType.COLOR,
            payload = result
        )
    }

    private fun sampleCenterRgb(image: ImageProxy): Triple<Int, Int, Int>? {
        val yPlane = image.planes[0].buffer
        if (!yPlane.hasRemaining()) return null

        val y = yPlane.get(0).toInt() and 0xFF
        return Triple(y, y, y) // grayscale → RGB
    }

    private fun rgbToHsv(r: Int, g: Int, b: Int): FloatArray {
        val hsv = FloatArray(3)
        android.graphics.Color.RGBToHSV(r, g, b, hsv)
        return hsv
    }
}