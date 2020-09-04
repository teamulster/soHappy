package de.hsaugsburg.teamulster.sohappy.analyzer

import android.graphics.Bitmap
import android.graphics.Rect

@Suppress("UtilityClassWithPublicConstructor")
class ImageEditor {
    companion object {
        @Suppress("FunctionOnlyReturningConstant")
        fun crop (img: Bitmap, frame: Rect): Bitmap? {
            return null
        }
    }
    // NOTE: Perhaps use TFLite ImageProcessor for Cropping/Resizing/Rotating?!
}
