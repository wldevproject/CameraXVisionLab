package com.cnd.cameraxvisionlab.core.analyzer

data class FrameData(
    val width: Int,
    val height: Int,
    val rotationDegrees: Int,
    val timestamp: Long
)