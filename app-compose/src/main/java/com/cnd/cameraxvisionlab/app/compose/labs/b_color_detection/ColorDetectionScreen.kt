package com.cnd.cameraxvisionlab.app.compose.labs.b_color_detection

import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.cnd.cameraxvisionlab.core.camerax.CameraXProvider
import com.cnd.cameraxvisionlab.core.vision.color.ColorProbeAnalyzer

@Composable
fun ColorDetectionScreen(
    cameraProvider: CameraXProvider
) {
    var colorText by remember { mutableStateOf("Detecting...") }

    Box(modifier = Modifier.fillMaxSize()) {

        // Camera preview
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {
                    val analyzer = ColorProbeAnalyzer { result ->
                        colorText = result.category.name
                    }

                    cameraProvider.startCamera(
                        previewView = this,
                        analyzer = analyzer
                    )
                }
            }
        )

        // Overlay text
        Text(
            text = colorText,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
