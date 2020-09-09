package de.hsaugsburg.teamulster.sohappy.queue

import android.graphics.Bitmap

class BitmapQueue {
    companion object {
        internal class BitmapWrapper {
            var bitmap : Bitmap? = null

            fun poll(): Bitmap? {
                val tmp = bitmap
                bitmap = null
                return tmp
            }

            fun set(bitmap: Bitmap) {
                this.bitmap = bitmap
            }
        }
    }
    internal val internalBitmapWrapper = BitmapWrapper()

    /**
     * Tries to removes the current bitmap from the queue and adds bitmap to the queue
     * The old bitmap will be recycled
     * @param bitmap: Bitmap to add
     */
    fun replace(bitmap: Bitmap) {
        synchronized(internalBitmapWrapper) {
            val oldBitmap = internalBitmapWrapper.poll()
            oldBitmap?.recycle()
            internalBitmapWrapper.set(bitmap)
            (internalBitmapWrapper as Object).notify()
        }
    }

    /**
     * polls the latest bitmap from queue; blocks/wait until bitmap is available
     */
    fun poll(): Bitmap? {
        synchronized(internalBitmapWrapper) {
            if (internalBitmapWrapper.bitmap == null) {
                (internalBitmapWrapper as Object).wait()
            }
            return internalBitmapWrapper.poll()
        }
    }
}
