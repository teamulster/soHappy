package de.hsaugsburg.teamulster.sohappy.analyzer

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect

@Suppress("UtilityClassWithPublicConstructor")
class ImageEditor {
    companion object {
        @Suppress("FunctionOnlyReturningConstant")
        fun crop (img: Bitmap, frame: Rect): Bitmap? {
            return null
        }

        fun rotate (bitmap: Bitmap, degree: Float): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(-90F)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)
            return Bitmap.createBitmap(scaledBitmap, 0, 0,  scaledBitmap.width,
                scaledBitmap.height, matrix, true)
        }
    }
}
