package com.cnd.cameraxvisionlab.app.view.preview

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cnd.cameraxvisionlab.app.view.R
import com.cnd.cameraxvisionlab.core.camerax.CameraConfig
import com.cnd.cameraxvisionlab.core.camerax.CameraXProvider
import com.cnd.cameraxvisionlab.core.vision.color.ColorCalibration
import com.cnd.cameraxvisionlab.core.vision.color.ColorCategory
import com.cnd.cameraxvisionlab.core.vision.color.ColorRange
import com.cnd.cameraxvisionlab.core.vision.color.HsvColorAnalyzer

class PreviewActivity : AppCompatActivity() {
    private val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
    private val REQUEST_CODE_CAMERA = 1001

    private lateinit var cameraProvider: CameraXProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        // cek permission DI SINI
        if (checkSelfPermission(CAMERA_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(CAMERA_PERMISSION), REQUEST_CODE_CAMERA)
            return
        }

        // init kamera
        startCamera()
    }

    // CALLBACK PERMISSION
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
            startCamera() // ⬅️ langsung start, LEBIH BAIK dari recreate()
        }
    }

    // fungsi bantu
    private fun startCamera() {
        val previewView = findViewById<PreviewView>(R.id.previewView)
        val txtColor = findViewById<TextView>(R.id.txtColor)

        val redCalibration = ColorCalibration(
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

        val analyzer = HsvColorAnalyzer(
            calibration = redCalibration
        ) { result ->
            runOnUiThread {
                txtColor.text = "Color: ${result.category}"
            }
        }

        cameraProvider = CameraXProvider(
            context = this,
            lifecycleOwner = this,
            config = CameraConfig()
        )

        previewView.post {
            cameraProvider.startCamera(
                previewView = previewView,
                analyzer = analyzer
            )
        }
    }

    // cleanup
    override fun onDestroy() {
        super.onDestroy()
        cameraProvider.stopCamera()
    }
}