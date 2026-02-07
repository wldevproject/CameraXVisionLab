package com.cnd.cameraxvisionlab.app.view.labs.b_color_detection

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import com.cnd.cameraxvisionlab.app.view.R
import com.cnd.cameraxvisionlab.core.camerax.CameraXProvider
import com.cnd.cameraxvisionlab.core.vision.color.ColorProbeAnalyzer

class ColorDetectionActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var txtColor: TextView
    private lateinit var cameraProvider: CameraXProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_detection)

        previewView = findViewById(R.id.previewView)
        txtColor = findViewById(R.id.txtColor)

        cameraProvider = CameraXProvider(
            context = this,
            lifecycleOwner = this
        )

        val analyzer = ColorProbeAnalyzer { result ->
            runOnUiThread {
                txtColor.text = result.category.name
            }
        }

        cameraProvider.startCamera(
            previewView = previewView,
            analyzer = analyzer
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraProvider.stopCamera()
    }
}