package com.cnd.cameraxvisionlab.app.view.overlay

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TapOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var onTap: ((x: Float, y: Float) -> Unit)? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            onTap?.invoke(event.x, event.y)
            return true
        }
        return super.onTouchEvent(event)
    }
}