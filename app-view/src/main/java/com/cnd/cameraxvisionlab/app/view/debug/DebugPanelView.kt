package com.cnd.cameraxvisionlab.app.view.debug

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.cnd.cameraxvisionlab.core.vision.color.ColorCategory

class DebugPanelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val txtHsv = TextView(context)
    private val txtStatus = TextView(context)
    private val btnRed = Button(context).apply { text = "Set RED" }

    var onSetColor: ((ColorCategory) -> Unit)? = null

    init {
        orientation = VERTICAL
        setPadding(16, 16, 16, 16)

        addView(txtHsv)
        addView(txtStatus)
        addView(btnRed)

        btnRed.setOnClickListener {
            onSetColor?.invoke(ColorCategory.RED)
        }
    }

    fun updateHsv(h: Float, s: Float, v: Float) {
        txtHsv.text =
            "H:${h.toInt()}  S:${"%.2f".format(s)}  V:${"%.2f".format(v)}"
    }

    fun updateResult(category: ColorCategory) {
        txtStatus.text = "Detected: ${category.name}"
    }
}