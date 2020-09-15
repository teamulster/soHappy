package de.hsaugsburg.teamulster.sohappy.analyzer

import android.graphics.*
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Provides a toolbox for editing a bitmap, like cropping, rotating, resizing and grey scaling.
 */
object BitmapEditor {

    /**
     * Note that the constructor of Rect takes right and bottom instead of height and width.
     * Right is the same as left+width and bottom is the same as top+height.
     *
     * @param frame is the rectangle you want to cut out.
     * @return a cutout from the bitmap.
     */
    fun crop(bitmap: Bitmap, frame: Rect): Bitmap? =
        Bitmap.createBitmap(bitmap, frame.left, frame.top, frame.width(), frame.height())

    /**
     * @param bitmap you want rotate.
     * @param degree is the number of degree you rotate, positive values rotate clockwise.
     * @return a new rotated bitmap.
     */
    fun rotate(bitmap: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    /**
     * @param bitmap you want to resize.
     * @param width you want to have.
     * @param height you want to have.
     * @return a new scaled bitmap.
     */
    fun resize(bitmap: Bitmap, width: Int, height: Int): Bitmap =
        Bitmap.createScaledBitmap(bitmap, width, height, false)

    /**
     * @param bitmap you want to greyscale.
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
     * This private function converts a given Bitmap into a ByteBuffer.
     *
     * @param bitmap a Bitmap which will be converted into a ByteBuffer
     * @return ByteBuffer?
     * */
    fun convertToByteBuffer(bitmap: Bitmap): ByteBuffer? {
        val width = bitmap.width
        val height = bitmap.height
        val mImgData: ByteBuffer = ByteBuffer
            .allocateDirect(4 * width * height)
        mImgData.order(ByteOrder.nativeOrder())
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        for (pixel in pixels) {
            mImgData.putFloat(Color.red(pixel).toFloat())
        }
        return mImgData
    }
}
