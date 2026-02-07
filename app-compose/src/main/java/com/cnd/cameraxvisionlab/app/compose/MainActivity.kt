package com.cnd.cameraxvisionlab.app.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cnd.cameraxvisionlab.app.compose.preview.PreviewScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewScreen()
        }
    }
}