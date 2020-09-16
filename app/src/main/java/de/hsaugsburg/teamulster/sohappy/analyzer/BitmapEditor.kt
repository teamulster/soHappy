package de.hsaugsburg.teamulster.sohappy.analyzer

import android.graphics.*

/**
 * Provides a toolbox for editing a bitmap, like cropping, rotating, resizing and grey scaling.
 */
object BitmapEditor {

    /**
     * Note that the constructor of Rect takes right and bottom instead of height and width.
     * Right is the same as left+width and bottom is the same as top+height.
     *
     * @param frame is the rectangle to be cut.
     * @return a cutout from the bitmap.
     */
    fun crop(bitmap: Bitmap, frame: Rect): Bitmap? {
        var width = frame.width()
        if (bitmap.width < frame.left + frame.width()) {
            width = bitmap.width - frame.left
        }
        var height = frame.height()
        if (bitmap.height < frame.top + frame.height()) {
            height = bitmap.height - frame.top
        }
        var left = frame.left
        if (frame.left < 0) {
            left = 0
        }
        var top = frame.top
        if (frame.top < 0) {
            top = 0
        }
        return Bitmap.createBitmap(bitmap, left, top, width, height)
    }

    /**
     * @param bitmap to be rotated.
     * @param degree is the number of degrees to rotate (positive values rotate clockwise).
     * @return a new rotated bitmap.
     */
    fun rotate(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    /**
     * @param bitmap to be resized.
     * @param width of outcome bitmap.
     * @param height of outcome bitmap.
     * @return a new scaled bitmap.
     */
    fun resize(bitmap: Bitmap, width: Int, height: Int): Bitmap =
        Bitmap.createScaledBitmap(bitmap, width, height, false)

    /**
     * @param bitmap to be greyscaled.
     * @return a new grey scaled bitmap.
     */
    fun greyScale(bitmap: Bitmap): Bitmap {
        val copiedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val bitmapGreyScale =
            Bitmap.createBitmap(copiedBitmap.width, copiedBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmapGreyScale)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val colorMatrixFilter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = colorMatrixFilter
        canvas.drawBitmap(copiedBitmap, 0F, 0F, paint)
        return bitmapGreyScale
    }

    /**
     * Flips a Bitmap in horizontal direction.
     *
     * @param bitmap to be flipped.
     * @return the flipped bitmap.
     */
    fun flipHorizontal(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.preScale(-1.0f, 1.0f)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}

