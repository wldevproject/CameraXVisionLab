package com.cnd.cameraxvisionlab

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnClassic).setOnClickListener {
            launchClassic()
        }

        findViewById<Button>(R.id.btnCompose).setOnClickListener {
            launchCompose()
        }
    }

    private fun launchClassic() {
        val pm = packageManager
        val intent = pm.getLaunchIntentForPackage(
            "com.cnd.cameraxvisionlab.app.view"
        )

        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Classic app not installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchCompose() {
        val pm = packageManager
        val intent = pm.getLaunchIntentForPackage(
            "com.cnd.cameraxvisionlab.app.compose"
        )

        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Compose app not installed", Toast.LENGTH_SHORT).show()
        }
    }
}