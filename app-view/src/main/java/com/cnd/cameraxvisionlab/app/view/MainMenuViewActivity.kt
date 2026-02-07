package com.cnd.cameraxvisionlab.app.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cnd.cameraxvisionlab.app.view.labs.a_camera_preview.CameraPreviewActivity
import com.cnd.cameraxvisionlab.app.view.labs.b_color_detection.ColorDetectionActivity
import com.cnd.cameraxvisionlab.app.view.labs.c_object_detection.ObjectDetectionActivity
import com.cnd.cameraxvisionlab.app.view.labs.d_position_detection.PositionDetectionActivity
import com.cnd.cameraxvisionlab.app.view.labs.e_distance_estimation.DistanceEstimationActivity
import com.cnd.cameraxvisionlab.app.view.labs.f_object_tracking.ObjectTrackingActivity
import com.cnd.cameraxvisionlab.app.view.labs.g_face_detection.FaceDetectionActivity
import com.cnd.cameraxvisionlab.app.view.labs.h_document_scanner.DocumentScannerActivity
import com.cnd.cameraxvisionlab.app.view.labs.i_qr_scanner.QrScannerActivity
import com.cnd.cameraxvisionlab.app.view.labs.j_debug_analyzer.DebugAnalyzerActivity

class MainMenuViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bind(R.id.btnCameraPreview, CameraPreviewActivity::class.java)
        bind(R.id.btnColorDetection, ColorDetectionActivity::class.java)
        bind(R.id.btnObjectDetection, ObjectDetectionActivity::class.java)
        bind(R.id.btnPositionDetection, PositionDetectionActivity::class.java)
        bind(R.id.btnDistanceEstimation, DistanceEstimationActivity::class.java)
        bind(R.id.btnObjectTracking, ObjectTrackingActivity::class.java)
        bind(R.id.btnFaceDetection, FaceDetectionActivity::class.java)
        bind(R.id.btnDocumentScanner, DocumentScannerActivity::class.java)
        bind(R.id.btnQrScanner, QrScannerActivity::class.java)
        bind(R.id.btnDebugAnalyzer, DebugAnalyzerActivity::class.java)
    }

    private fun bind(buttonId: Int, target: Class<*>) {
        findViewById<android.view.View>(buttonId).setOnClickListener {
            startActivity(Intent(this, target))
        }
    }
}