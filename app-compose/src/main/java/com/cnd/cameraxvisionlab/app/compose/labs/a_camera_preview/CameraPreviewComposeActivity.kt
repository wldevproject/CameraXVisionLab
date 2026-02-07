package com.cnd.cameraxvisionlab.app.compose.labs.a_camera_preview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cnd.cameraxvisionlab.core.camerax.CameraXProvider

class CameraPreviewComposeActivity : ComponentActivity() {

    private val CAMERA_PERMISSION = Manifest.permission.CAMERA
    private val REQUEST_CODE_CAMERA = 1001

    private lateinit var cameraProvider: CameraXProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraProvider = CameraXProvider(
            context = this,
            lifecycleOwner = this
        )

        if (ContextCompat.checkSelfPermission(this, CAMERA_PERMISSION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(CAMERA_PERMISSION),
                REQUEST_CODE_CAMERA
            )
        } else {
            setCameraContent()
        }
    }

    private fun setCameraContent() {
        setContent {
            CameraPreviewScreen(cameraProvider)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_CAMERA &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            setCameraContent()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraProvider.stopCamera()
    }
}