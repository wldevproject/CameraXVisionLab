package com.cnd.cameraxvisionlab.app.compose.calibrate.preview

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.cnd.cameraxvisionlab.core.camerax.CameraConfig
import com.cnd.cameraxvisionlab.core.camerax.CameraXProvider
import com.cnd.cameraxvisionlab.core.vision.color.*

@Composable
fun PreviewScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // 🔐 Runtime permission
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (!hasCameraPermission) {
        Text("Waiting for camera permission…")
        return
    }

    var colorText by remember { mutableStateOf("Color: -") }

    // 🔴 Hardcoded calibration (sementara, valid)
    val redCalibration = remember {
        ColorCalibration(
            category = ColorCategory.RED,
            range = ColorRange(
                hMin = 0f,
                hMax = 15f,
                sMin = 0.5f,
                sMax = 1f,
                vMin = 0.4f,
                vMax = 1f
            )
        )
    }

    // ✅ SIGNATURE BENAR (2 PARAMETER)
    val analyzer = remember {
        HsvColorAnalyzer(
            calibration = redCalibration
        ) { result, _ ->
            colorText = "Color: ${result.category}"
        }
    }

    Box(Modifier.fillMaxSize()) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PreviewView(it).also { pv ->
                    val provider = CameraXProvider(
                        context = it,
                        lifecycleOwner = lifecycleOwner,
                        config = CameraConfig()
                    )

                    pv.post {
                        provider.startCamera(
                            previewView = pv,
                            analyzer = analyzer
                        )
                    }
                }
            }
        )

        Text(
            text = colorText,
            modifier = Modifier.padding(16.dp)
        )
    }
}