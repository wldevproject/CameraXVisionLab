package com.cnd.cameraxvisionlab.app.compose.labs.a_camera_preview

import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.cnd.cameraxvisionlab.core.camerax.CameraXProvider

@Composable
fun CameraPreviewScreen(
    cameraProvider: CameraXProvider
) {
    AndroidView(
        modifier = Modifier,
        factory = { context ->
            PreviewView(context).apply {
                cameraProvider.startCamera(
                    previewView = this,
                    analyzer = null
                )
            }
        }
    )
}