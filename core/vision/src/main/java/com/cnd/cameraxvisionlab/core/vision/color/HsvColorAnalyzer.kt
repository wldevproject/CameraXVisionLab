package com.cnd.cameraxvisionlab.core.vision.color

import android.graphics.ImageFormat
import androidx.camera.core.ImageProxy
import com.cnd.cameraxvisionlab.core.analyzer.FrameData
import com.cnd.cameraxvisionlab.core.analyzer.VisionResult
import com.cnd.cameraxvisionlab.core.analyzer.VisionType
import com.cnd.cameraxvisionlab.core.camerax.ImageAnalyzerBase
import java.nio.ByteBuffer

class HsvColorAnalyzer(
    private val calibration: ColorCalibration,
    private val onDetected: (ColorResult) -> Unit
) : ImageAnalyzerBase() {

    override fun analyzeFrame(
        image: ImageProxy,
        frameData: FrameData
    ): VisionResult? {

        if (image.format != ImageFormat.YUV_420_888) return null

        // Ambil pixel tengah (sederhana & cepat)
        val (r, g, b) = sampleCenterRgb(image) ?: return null
        val hsv = rgbToHsv(r, g, b)

        val range = calibration.range
        val match =
            hsv[0] in range.hMin..range.hMax &&
                    hsv[1] in range.sMin..range.sMax &&
                    hsv[2] in range.vMin..range.vMax

        val result = ColorResult(
            category = if (match) calibration.category else ColorCategory.UNKNOWN,
            confidence = if (match) 0.85f else 0.2f
        )

        onDetected(result)

        return VisionResult(
            type = VisionType.COLOR,
            payload = result
        )
    }

    private fun sampleCenterRgb(image: ImageProxy): Triple<Int, Int, Int>? {
        val yPlane = image.planes[0].buffer
        if (!yPlane.hasRemaining()) return null

        val y = yPlane.get(0).toInt() and 0xFF
        // Simplified grayscale → RGB
        return Triple(y, y, y)
    }

    private fun rgbToHsv(r: Int, g: Int, b: Int): FloatArray {
        val hsv = FloatArray(3)
        android.graphics.Color.RGBToHSV(r, g, b, hsv)
        return hsv
    }
}