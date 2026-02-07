package com.cnd.cameraxvisionlab.core.vision.color

data class ColorResult(
    val category: ColorCategory,
    val confidence: Float,
    val hsv: FloatArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ColorResult

        if (confidence != other.confidence) return false
        if (category != other.category) return false
        if (!hsv.contentEquals(other.hsv)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = confidence.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + hsv.contentHashCode()
        return result
    }
}