package com.cnd.cameraxvisionlab.app.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cnd.cameraxvisionlab.app.compose.labs.MainMenuScreen

class MainMenuComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainMenuScreen { target ->
                startActivity(Intent(this, target))
            }
        }
    }
}