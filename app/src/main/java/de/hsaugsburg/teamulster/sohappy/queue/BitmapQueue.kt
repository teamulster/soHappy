package de.hsaugsburg.teamulster.sohappy.queue

import android.graphics.Bitmap
import com.google.common.collect.EvictingQueue

class BitmapQueue {
    internal val internalEvictingQueue = EvictingQueue.create<Bitmap>(1)

    /**
     * Tries to removes the current bitmap from the queue and adds bitmap to the queue
     * The old bitmap will be recycled
     * @param bitmap: Bitmap to add
     */
    fun replace(bitmap: Bitmap) {
        synchronized(internalEvictingQueue) {
            val oldBitmap = internalEvictingQueue.poll()
            oldBitmap?.recycle()
            internalEvictingQueue.add(bitmap)
            (internalEvictingQueue as Object).notify()
        }
    }

    /**
     * polls the latest bitmap from queue; blocks/wait until bitmap is available
     */
    fun poll(): Bitmap? {
        synchronized(internalEvictingQueue) {
            if (internalEvictingQueue.size == 0) {
                (internalEvictingQueue as Object).wait()
            }
            return internalEvictingQueue.poll()
        }
    }
}
