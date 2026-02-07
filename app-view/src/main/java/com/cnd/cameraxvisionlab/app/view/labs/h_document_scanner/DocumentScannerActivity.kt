package com.cnd.cameraxvisionlab.app.view.labs.h_document_scanner

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cnd.cameraxvisionlab.app.view.R

class DocumentScannerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(
            android.widget.TextView(this).apply {
                text = "Document Scanner (Coming Soon)"
                textSize = 20f
                gravity = android.view.Gravity.CENTER
            }
        )
    }
}