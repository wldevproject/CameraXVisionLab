package com.cnd.cameraxvisionlab.core.analyzer

data class VisionResult(
    val type: VisionType,
    val payload: Any
)

enum class VisionType {
    COLOR,
    POSITION,
    DISTANCE,
    TRACKING,
    FACE,
    DOCUMENT,
    QR
}