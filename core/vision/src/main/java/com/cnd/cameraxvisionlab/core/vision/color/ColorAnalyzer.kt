package com.cnd.cameraxvisionlab.core.vision.color

import androidx.camera.core.ImageProxy
import com.cnd.cameraxvisionlab.core.analyzer.FrameData
import com.cnd.cameraxvisionlab.core.analyzer.VisionResult
import com.cnd.cameraxvisionlab.core.analyzer.VisionType
import com.cnd.cameraxvisionlab.core.camerax.ImageAnalyzerBase
import java.nio.ByteBuffer

class ColorAnalyzer(
    private val onColorDetected: (ColorResult) -> Unit
) : ImageAnalyzerBase() {

    override fun analyzeFrame(
        image: ImageProxy,
        frameData: FrameData
    ): VisionResult {

        val yPlane = image.planes[0].buffer
        val avgLuma = averageLuma(yPlane)

        val result = when {
            avgLuma < 40 -> ColorCategory.BLACK
            avgLuma > 220 -> ColorCategory.WHITE
            else -> ColorCategory.UNKNOWN
        }

        val colorResult = ColorResult(
            category = result,
            confidence = 0.7f
        )

        onColorDetected(colorResult)

        return VisionResult(
            type = VisionType.COLOR,
            payload = colorResult
        )
    }

    private fun averageLuma(buffer: ByteBuffer): Int {
        var sum = 0
        val count = buffer.remaining()
        while (buffer.hasRemaining()) {
            sum += buffer.get().toInt() and 0xFF
        }
        return if (count == 0) 0 else sum / count
    }
}