package com.cnd.cameraxvisionlab.app.view.labs.a_camera_preview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import com.cnd.cameraxvisionlab.app.view.R
import com.cnd.cameraxvisionlab.core.camerax.CameraXProvider

class CameraPreviewActivity : AppCompatActivity() {
    private val CAMERA_PERMISSION = Manifest.permission.CAMERA
    private val REQUEST_CODE_CAMERA = 1001

    private lateinit var previewView: PreviewView
    private lateinit var cameraProvider: CameraXProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_preview)

        previewView = findViewById(R.id.previewView)

        if (checkSelfPermission(CAMERA_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(CAMERA_PERMISSION), REQUEST_CODE_CAMERA)
        } else {
            startCamera()
        }
    }


    private fun startCamera(){
        cameraProvider = CameraXProvider(
            context = this,
            lifecycleOwner = this
        )

        cameraProvider.startCamera(
            previewView = previewView,
            analyzer = null
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraProvider.stopCamera()
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
            startCamera()
        }
    }
}