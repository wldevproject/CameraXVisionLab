package com.cnd.core_camerax

import androidx.camera.core.CameraSelector

data class CameraConfig(
    val lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    val enablePreview: Boolean = true,
    val enableImageAnalysis: Boolean = true,
    val targetFrameRate: Int = 30
)