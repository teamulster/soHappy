package de.hsaugsburg.teamulster.sohappy.analyzer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.Paint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter

/**
 * provides a toolbox for editing a bitmap, like cropping, rotating, resizing and grey scaling
 */
object BitmapEditor {
    fun crop(bitmap: Bitmap, frame: Rect): Bitmap? {
        /**
         * @param frame
         * frame.left is used as the left x coordinate of the cropped bitmap
         * frame.top -> top y
         * frame.right -> width x
         * frame.bottom -> height
         */
        return Bitmap.createBitmap(bitmap, frame.left, frame.top, frame.width(), frame.height())
    }

    fun rotate(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun resize(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }

    fun greyscale(bitmap: Bitmap): Bitmap {
        val bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val bitmapGrayScale =
            Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapGrayScale)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val colorMatrixFilter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = colorMatrixFilter
        canvas.drawBitmap(bitmap, 0F, 0F, paint)
        return bitmapGrayScale
    }
}
