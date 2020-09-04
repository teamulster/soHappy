package de.hsaugsburg.teamulster.sohappy

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.analyzer.BitmapEditor
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotSame
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BitmapEditorTest {

    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context
    private val inputStream = instrumentationContext.assets.open("negative-test.jpg")
    private val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

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

        assertEquals(20-3, croppedBitmap!!.width)
        assertEquals(25-5, croppedBitmap.height)

        assertNotSame(20-1, croppedBitmap.width)
        assertNotSame(25-1, croppedBitmap.height)
    }

    @Test
    fun testRotate() {
        val height = bitmap.height
        val width = bitmap.width

        val rotatedBitmap = BitmapEditor.rotate(bitmap, 90f)

        assertEquals(height, rotatedBitmap.width)
        assertEquals(width, rotatedBitmap.height)

        assertNotSame(height, rotatedBitmap.width)
        assertNotSame(height, rotatedBitmap.height)
    }

    @Test
    fun testResize() {
        val height = 40
        val width = 30

        val resizeBitmap = BitmapEditor.resize(bitmap, width, height)

        assert(resizeBitmap.width == width && resizeBitmap.height == height)
    }

    @Test
    fun testGreyScale() {
        val bitmap = BitmapEditor.greyscale(bitmap)

        // for greyscale, rgb values are all equal
        for (x in 0 until bitmap.height) {
            for (y in 0 until bitmap.width) {
                assert(bitmap.getColor(x,y).red() == bitmap.getColor(x,y).green()
                        && bitmap.getColor(x,y).green() == bitmap.getColor(x,y).blue())
            }
        }
    }
}
