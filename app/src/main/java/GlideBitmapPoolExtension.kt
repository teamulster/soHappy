import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import com.glidebitmappool.GlideBitmapPool

object GlideBitmapPoolExtension {
    fun copy(src: Bitmap): Bitmap {
        val returnValue = GlideBitmapPool.getBitmap(src.width, src.height, src.config)
        val canvas = Canvas(returnValue)
        canvas.drawBitmap(src, Matrix(), null)
        return returnValue
    }
}