package com.cnd.cameraxvisionlab.core.analyzer

data class DebugInfo(
    val fps: Float,
    val frameTimeMs: Long,
    val notes: String? = null
)