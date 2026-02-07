package com.cnd.cameraxvisionlab.app.view.preview

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import com.cnd.cameraxvisionlab.app.view.R
import com.cnd.cameraxvisionlab.app.view.color.ColorCalibrationController
import com.cnd.cameraxvisionlab.app.view.debug.DebugPanelView
import com.cnd.cameraxvisionlab.app.view.overlay.TapOverlayView
import com.cnd.cameraxvisionlab.core.camerax.CameraConfig
import com.cnd.cameraxvisionlab.core.camerax.CameraXProvider
import com.cnd.cameraxvisionlab.core.vision.color.HsvColorAnalyzer

class PreviewActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
    private val REQUEST_CODE_CAMERA = 1001

    private lateinit var previewView: PreviewView
    private lateinit var tapOverlay: TapOverlayView
    private lateinit var debugPanel: DebugPanelView

    private var lastHsv: FloatArray? = null
    private val calibrationController = ColorCalibrationController()

    private lateinit var cameraProvider: CameraXProvider
    private var analyzer: HsvColorAnalyzer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        previewView = findViewById(R.id.previewView)
        tapOverlay = findViewById(R.id.tapOverlay)
        debugPanel = findViewById(R.id.debugPanel)

        if (checkSelfPermission(CAMERA_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(CAMERA_PERMISSION), REQUEST_CODE_CAMERA)
            return
        }

        startCamera()
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

    private fun startCamera() {
        cameraProvider = CameraXProvider(
            context = this,
            lifecycleOwner = this,
            config = CameraConfig()
        )

        analyzer = HsvColorAnalyzer(
            calibration = calibrationController.current
        ) { result, hsv ->

            runOnUiThread {
                // tampilkan HSV di debug panel
                debugPanel.updateHsv(hsv[0], hsv[1], hsv[2])
                debugPanel.updateResult(result.category)
                lastHsv = hsv
            }
        }

        tapOverlay.onTap = { _, _ ->
            lastHsv?.let {
                debugPanel.updateHsv(it[0], it[1], it[2])
            }
        }

        debugPanel.onSetColor = { category ->
            lastHsv?.let {
                calibrationController.updateFromHsv(category, it)
                debugPanel.updateResult(category)
                cameraProvider.stopCamera()
                startCamera()
            }
        }

        previewView.post {
            cameraProvider.startCamera(
                previewView = previewView,
                analyzer = analyzer
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraProvider.stopCamera()
    }
}