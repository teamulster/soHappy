import android.R.attr
import android.graphics.*
import com.glidebitmappool.GlideBitmapPool


object GlideBitmapPoolExtension {
    fun copy(src: Bitmap): Bitmap {
        val returnValue = GlideBitmapPool.getBitmap(src.width, src.height, src.config)
        val canvas = Canvas(returnValue)
        canvas.drawBitmap(src, Matrix(), null)
        return returnValue
    }
    
    fun getBitmap(
        source: Bitmap, x: Int, y: Int, width: Int, height: Int, m: Matrix, filter: Boolean
    ): Bitmap {
        // This is a straight up copy of:
        // https://android.googlesource.com/platform/frameworks/base/+/refs/heads/android10-release/graphics/java/android/graphics/Bitmap.java
        // Line 870+
        // TODO: Since we actually only use ARGB888, should we really keep the other code?
        require(x + width <= source.width) { "x + width must be <= bitmap.width()" }
        require(y + height <= source.height) { "y + height must be <= bitmap.height()" }
        require(!source.isRecycled) { "cannot use a recycled source in createBitmap" }
        // check if we can just return our argument unchanged
        // check if we can just return our argument unchanged
        if (!source.isMutable && x == 0 && y == 0 && width == source.width && height == source.height && (m.isIdentity)) {
            return source
        }
        var neww = width
        var newh = height
        val bitmap: Bitmap
        val paint: Paint?
        val srcR = Rect(x, y, x + width, y + height)
        val dstR = RectF(0F, 0F, width.toFloat(), height.toFloat())
        val deviceR = RectF()
        var newConfig: Bitmap.Config = Bitmap.Config.ARGB_8888
        val config: Bitmap.Config? = source.config
        // GIF files generate null configs, assume ARGB_8888
        if (config != null) {
            newConfig = when (config) {
                Bitmap.Config.RGB_565 -> Bitmap.Config.RGB_565
                Bitmap.Config.ALPHA_8 -> Bitmap.Config.ALPHA_8
                Bitmap.Config.ARGB_4444, Bitmap.Config.ARGB_8888 -> Bitmap.Config.ARGB_8888
                else -> Bitmap.Config.ARGB_8888
            }
        }
        if (m.isIdentity) {
            bitmap = GlideBitmapPool.getBitmap(neww, newh, newConfig)
            paint = null // not needed
        } else {
            val transformed = !m.rectStaysRect()
            m.mapRect(deviceR, dstR)
            neww = Math.round(deviceR.width())
            newh = Math.round(deviceR.height())
            var transformedConfig: Bitmap.Config = newConfig
            if (transformed) {
                if (transformedConfig !== Bitmap.Config.ARGB_8888) {
                    transformedConfig = Bitmap.Config.ARGB_8888
                }
            }
            bitmap = GlideBitmapPool.getBitmap(
                neww, newh, transformedConfig
            )
            paint = Paint()
            paint.isFilterBitmap = filter
            if (transformed) {
                paint.isAntiAlias = true
            }
        }
        // The new bitmap was created from a known bitmap source so assume that
        // they use the same density
        bitmap.density = source.density
        bitmap.setHasAlpha(source.hasAlpha())
        val canvas = Canvas(bitmap)
        canvas.translate(-deviceR.left, -deviceR.top)
        canvas.concat(m)
        canvas.drawBitmap(source, srcR, dstR, paint)
        canvas.setBitmap(null)
        return bitmap
    }
}
