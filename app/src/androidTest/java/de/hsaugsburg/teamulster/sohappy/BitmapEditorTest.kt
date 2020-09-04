package de.hsaugsburg.teamulster.sohappy

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.analyzer.BitmapEditor
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BitmapEditorTest {

    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context
    lateinit var bitmap: Bitmap

    @Before
    fun setUp(){
        val inputStream = instrumentationContext.assets.open("negative-test.jpg")
        bitmap = BitmapFactory.decodeStream(inputStream)
    }

    @Test
    fun testCrop() {
        // test whether width and height are changed correctly
        var rect = Rect(0, 0, 20, 25)
        var croppedBitmap = BitmapEditor.crop(bitmap, rect)

        assertEquals(20, croppedBitmap!!.width)
        assertEquals(25, croppedBitmap.height)

        assertNotSame(1, croppedBitmap.width)
        assertNotSame(1, croppedBitmap.height)

        // test whether width and height are shifted correctly
        rect = Rect(3, 5, 20, 25)
        croppedBitmap = BitmapEditor.crop(bitmap, rect)

        assertEquals(20 - 3, croppedBitmap!!.width)
        assertEquals(25 - 5, croppedBitmap.height)

        assertNotSame(20 - 1, croppedBitmap.width)
        assertNotSame(25 - 1, croppedBitmap.height)
    }

    @Test
    fun testRotate() {
        // only works if the picture has not the same height and width
        val rotatedBitmap = BitmapEditor.rotate(bitmap, 90f)

        assertEquals(bitmap.height, rotatedBitmap.width)
        assertEquals(bitmap.width, rotatedBitmap.height)

        assertNotSame(bitmap.height, rotatedBitmap.width)
        assertNotSame(bitmap.height, rotatedBitmap.height)
    }

    @Test
    fun testResize() {
        val width = 30
        val height = 40

        val resizeBitmap = BitmapEditor.resize(bitmap, width, height)

        assertEquals(width, resizeBitmap.width)
        assertEquals(height, resizeBitmap.height)

        assertNotSame(width, resizeBitmap.height)
        assertNotSame(height, resizeBitmap.width)
    }

    @Test
    fun testGreyScale() {
        val greyScaleBitmap = BitmapEditor.greyscale(bitmap)

        // for greyscale, rgb values are all equal
        for (y in 0 until greyScaleBitmap.height) {
            for (x in 0 until greyScaleBitmap.width) {
                // for some reason assertEquals and assertNotSame are not working here
                assertTrue(greyScaleBitmap.getColor(x, y).red() == greyScaleBitmap.getColor(x, y).green())
                assertTrue(greyScaleBitmap.getColor(x, y).green() == greyScaleBitmap.getColor(x, y).blue())
            }
        }
    }
}
