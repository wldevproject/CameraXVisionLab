package com.cnd.cameraxvisionlab.app.compose.labs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cnd.cameraxvisionlab.app.compose.labs.a_camera_preview.CameraPreviewComposeActivity
import com.cnd.cameraxvisionlab.app.compose.labs.b_color_detection.ColorDetectionComposeActivity
import com.cnd.cameraxvisionlab.app.compose.labs.c_object_detection.ObjectDetectionComposeActivity
import com.cnd.cameraxvisionlab.app.compose.labs.d_position_detection.PositionDetectionComposeActivity
import com.cnd.cameraxvisionlab.app.compose.labs.e_distance_estimation.DistanceEstimationComposeActivity
import com.cnd.cameraxvisionlab.app.compose.labs.f_object_tracking.ObjectTrackingComposeActivity
import com.cnd.cameraxvisionlab.app.compose.labs.g_face_detection.FaceDetectionComposeActivity
import com.cnd.cameraxvisionlab.app.compose.labs.h_document_scanner.DocumentScannerComposeActivity
import com.cnd.cameraxvisionlab.app.compose.labs.i_qr_scanner.QrScannerComposeActivity
import com.cnd.cameraxvisionlab.app.compose.labs.j_debug_analyzer.DebugAnalyzerComposeActivity
import kotlin.jvm.java

@Composable
fun MainMenuScreen(
    onNavigate: (Class<*>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 26.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hi! I'm CameraX Compose version",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        MenuButton(
            text = "📸 Camera Preview",
            target = CameraPreviewComposeActivity::class.java,
            onNavigate = onNavigate
        )

        MenuButton(
            text = "🎨 Color Detection",
            target = ColorDetectionComposeActivity::class.java,
            onNavigate = onNavigate
        )

        MenuButton(
            text = "📦 Object Detection",
            target = ObjectDetectionComposeActivity::class.java,
            onNavigate = onNavigate
        )

        MenuButton(
            text = "📍 Position Detection",
            target = PositionDetectionComposeActivity::class.java,
            onNavigate = onNavigate
        )

        MenuButton(
            text = "📏 Distance Estimation",
            target = DistanceEstimationComposeActivity::class.java,
            onNavigate = onNavigate
        )

        MenuButton(
            text = "🔄 Object Tracking",
            target = ObjectTrackingComposeActivity::class.java,
            onNavigate = onNavigate
        )

        MenuButton(
            text = "🙂 Face Detection",
            target = FaceDetectionComposeActivity::class.java,
            onNavigate = onNavigate
        )

        MenuButton(
            text = "📄 Document Scanner",
            target = DocumentScannerComposeActivity::class.java,
            onNavigate = onNavigate
        )

        MenuButton(
            text = "🔳 QR / Barcode Scanner",
            target = QrScannerComposeActivity::class.java,
            onNavigate = onNavigate
        )

        MenuButton(
            text = "🧪 Debug and Analyzer",
            target = DebugAnalyzerComposeActivity::class.java,
            onNavigate = onNavigate
        )
    }
}
