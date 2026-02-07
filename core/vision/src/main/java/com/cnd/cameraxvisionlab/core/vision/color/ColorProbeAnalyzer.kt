package com.cnd.cameraxvisionlab.core.vision.color

import android.graphics.ImageFormat
import androidx.camera.core.ImageProxy
import com.cnd.cameraxvisionlab.core.analyzer.FrameData
import com.cnd.cameraxvisionlab.core.analyzer.VisionResult
import com.cnd.cameraxvisionlab.core.analyzer.VisionType
import com.cnd.cameraxvisionlab.core.camerax.ImageAnalyzerBase

class ColorProbeAnalyzer(
    private val onDetected: (ColorResult) -> Unit
) : ImageAnalyzerBase() {

    override fun analyzeFrame(
        image: ImageProxy,
        frameData: FrameData
    ): VisionResult? {

        if (image.format != ImageFormat.YUV_420_888) return null

        val rgb = sampleCenterRgb(image) ?: return null
        val hsv = rgbToHsv(rgb.first, rgb.second, rgb.third)

        val category = classifyColor(hsv)

        val result = ColorResult(
            category = category,
            confidence = 0.6f,
            hsv = hsv
        )

        onDetected(result)

        return VisionResult(
            type = VisionType.COLOR,
            payload = result
        )
    }

    private fun classifyColor(hsv: FloatArray): ColorCategory {
        val h = hsv[0]
        val s = hsv[1]
        val v = hsv[2]

        return when {
            v < 0.15f -> ColorCategory.BLACK
            v > 0.85f && s < 0.2f -> ColorCategory.WHITE
            s < 0.25f -> ColorCategory.GRAY

            h !in 15f..<345f -> ColorCategory.RED
            h < 45f -> ColorCategory.ORANGE
            h < 70f -> ColorCategory.YELLOW
            h < 160f -> ColorCategory.GREEN
            h < 260f -> ColorCategory.BLUE
            h < 320f -> ColorCategory.PURPLE
            else -> ColorCategory.UNKNOWN
        }
    }

    private fun sampleCenterRgb(image: ImageProxy): Triple<Int, Int, Int>? {
        val width = image.width
        val height = image.height

        val x = width / 2
        val y = height / 2

        val yPlane = image.planes[0]
        val uPlane = image.planes[1]
        val vPlane = image.planes[2]

        val yBuffer = yPlane.buffer
        val uBuffer = uPlane.buffer
        val vBuffer = vPlane.buffer

        val yRowStride = yPlane.rowStride
        val uvRowStride = uPlane.rowStride
        val uvPixelStride = uPlane.pixelStride

        val yIndex = y * yRowStride + x
        val uvIndex = (y / 2) * uvRowStride + (x / 2) * uvPixelStride

        val Y = yBuffer.get(yIndex).toInt() and 0xFF
        val U = (uBuffer.get(uvIndex).toInt() and 0xFF) - 128
        val V = (vBuffer.get(uvIndex).toInt() and 0xFF) - 128

        // YUV → RGB
        var r = (Y + 1.370705f * V).toInt()
        var g = (Y - 0.337633f * U - 0.698001f * V).toInt()
        var b = (Y + 1.732446f * U).toInt()

        r = r.coerceIn(0, 255)
        g = g.coerceIn(0, 255)
        b = b.coerceIn(0, 255)

        return Triple(r, g, b)
    }

    private fun rgbToHsv(r: Int, g: Int, b: Int): FloatArray {
        val hsv = FloatArray(3)
        android.graphics.Color.RGBToHSV(r, g, b, hsv)
        return hsv
    }
}
