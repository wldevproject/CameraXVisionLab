package com.cnd.cameraxvisionlab.core.camerax

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.cnd.cameraxvisionlab.core.analyzer.DebugInfo
import com.cnd.cameraxvisionlab.core.analyzer.FrameData
import com.cnd.cameraxvisionlab.core.analyzer.VisionResult
import kotlin.math.roundToInt

abstract class ImageAnalyzerBase : ImageAnalysis.Analyzer {

    private var lastFrameTimestamp: Long = 0L
    private var fps: Float = 0f

    final override fun analyze(image: ImageProxy) {
        val startTime = System.currentTimeMillis()

        val frameData = FrameData(
            width = image.width,
            height = image.height,
            rotationDegrees = image.imageInfo.rotationDegrees,
            timestamp = image.imageInfo.timestamp
        )

        val result = analyzeFrame(image, frameData)

        val frameTime = System.currentTimeMillis() - startTime
        updateFps()

        onResult(
            result = result,
            debugInfo = DebugInfo(
                fps = fps,
                frameTimeMs = frameTime
            )
        )

        image.close()
    }

    /**
     * Analyzer spesifik (warna, posisi, dll)
     */
    protected abstract fun analyzeFrame(
        image: ImageProxy,
        frameData: FrameData
    ): VisionResult?

    /**
     * Callback ke UI / consumer
     */
    protected open fun onResult(
        result: VisionResult?,
        debugInfo: DebugInfo
    ) {
        // default no-op
    }

    private fun updateFps() {
        val now = System.currentTimeMillis()
        if (lastFrameTimestamp != 0L) {
            val delta = now - lastFrameTimestamp
            if (delta > 0) {
                fps = (1000f / delta).roundToInt().toFloat()
            }
        }
        lastFrameTimestamp = now
    }
}