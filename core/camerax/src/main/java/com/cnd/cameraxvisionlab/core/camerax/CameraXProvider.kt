package com.cnd.cameraxvisionlab.core.camerax

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor

class CameraXProvider(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val config: CameraConfig = CameraConfig()
) {

    private var cameraProvider: ProcessCameraProvider? = null

    fun startCamera(
        previewView: PreviewView?,
        analyzer: ImageAnalysis.Analyzer? = null
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            bindUseCases(previewView, analyzer)
        }, mainExecutor())
    }

    fun stopCamera() {
        cameraProvider?.unbindAll()
    }

    private fun bindUseCases(
        previewView: PreviewView?,
        analyzer: ImageAnalysis.Analyzer?
    ) {
        val provider = cameraProvider ?: return
        provider.unbindAll()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(config.lensFacing)
            .build()

        val useCases = mutableListOf<UseCase>()

        if (config.enablePreview && previewView != null) {
            val preview = Preview.Builder().build().apply {
                surfaceProvider = previewView.surfaceProvider
            }
            useCases.add(preview)
        }

        if (config.enableImageAnalysis && analyzer != null) {
            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build().apply {
                    setAnalyzer(mainExecutor(), analyzer)
                }
            useCases.add(imageAnalysis)
        }

        provider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            *useCases.toTypedArray()
        )
    }

    private fun mainExecutor(): Executor =
        ContextCompat.getMainExecutor(context)
}