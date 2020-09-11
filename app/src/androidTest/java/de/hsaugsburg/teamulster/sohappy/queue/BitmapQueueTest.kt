package de.hsaugsburg.teamulster.sohappy.queue

import android.graphics.Bitmap
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import kotlin.concurrent.thread
import kotlin.random.Random

class BitmapQueueTest {
    private var bitmapQueue: BitmapQueue? = null

    @Before
    fun setUp() {
        bitmapQueue = BitmapQueue()
    }

    @Test
    // BitmapQueue always stores the latest object pushed in by "replace". Calling replace a few times
    // should therefore always give the latest Element
    fun replace() {
        val testBitmaps = (1..6).map { Bitmap.createBitmap(it, it, Bitmap.Config.ARGB_8888) }
        bitmapQueue!!.replace(testBitmaps[0])
        // Access the inner Queue and peek
        assertEquals(testBitmaps[0], bitmapQueue!!.internalBitmapWrapper.bitmap)
        bitmapQueue!!.replace(testBitmaps[1])
        bitmapQueue!!.replace(testBitmaps[2])
        bitmapQueue!!.replace(testBitmaps[3])
        bitmapQueue!!.replace(testBitmaps[4])
        bitmapQueue!!.replace(testBitmaps[5])
        assertEquals(testBitmaps[5], bitmapQueue!!.internalBitmapWrapper.bitmap)
    }

    @Test
    fun poll() {
        // poll will wait for an element, if no element is in the queue
        val waitingTime = Random.nextLong(50, 100)
        val originalBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
        thread {
            Thread.sleep(waitingTime)
            bitmapQueue!!.replace(originalBitmap)
        }
        val beforePoll = System.currentTimeMillis()
        val polledBitmap = bitmapQueue!!.poll()
        val afterPoll = System.currentTimeMillis()
        // poll must at least wait waitingTime or longer
        assertTrue(afterPoll - beforePoll >= waitingTime)
        // and the bitmaps should be the same
        assertEquals(originalBitmap, polledBitmap)
    }
}
