package com.cnd.cameraxvisionlab.app.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cnd.cameraxvisionlab.app.compose.calibrate.preview.PreviewScreen

class MainMenuComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewScreen()
        }
    }
}