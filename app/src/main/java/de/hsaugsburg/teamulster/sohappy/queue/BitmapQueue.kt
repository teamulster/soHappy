package de.hsaugsburg.teamulster.sohappy.queue

import android.graphics.Bitmap
import com.glidebitmappool.GlideBitmapPool
import com.google.common.collect.EvictingQueue

class BitmapQueue {
    val bitmapQueue = EvictingQueue.create<Bitmap>(1)

    /**
     * Tries to removes the current bitmap from the queue and adds bitmap to the queue
     * The old bitmap will be recycled
     * @param bitmap: Bitmap to add
     */
    fun replace(bitmap: Bitmap) {
        synchronized(bitmapQueue) {
            val oldBitmap = bitmapQueue.poll()
            if (oldBitmap != null) {
                GlideBitmapPool.putBitmap(oldBitmap)
            }
            bitmapQueue.add(bitmap)
            (bitmapQueue as Object).notify()
        }
    }

    /**
     * polls the latest bitmap from queue; blocks/wait until bitmap is available
     */
    fun poll(): Bitmap? {
        synchronized(bitmapQueue) {
            if (bitmapQueue.size == 0) {
                (bitmapQueue as Object).wait()
            }
            return bitmapQueue.poll()
        }
    }

}