package de.hsaugsburg.teamulster.sohappy

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.hsaugsburg.teamulster.sohappy.analyzer.BitmapEditor
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BitmapEditorTest {

    private val instrumentationContext: Context =
        InstrumentationRegistry.getInstrumentation().context
    private val inputStream = instrumentationContext.assets.open("test.jpg")
    private val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

    @Test
    fun testCrop() {
        val rect = Rect(0, 0, 20, 25)
        val cropedBitmap = BitmapEditor.crop(bitmap, rect)

        assert(cropedBitmap?.width == 20 && cropedBitmap.height == 25)
    }

    @Test
    fun testRotate() {
        val height = bitmap.height
        val width = bitmap.width

        val rotatedBitmap = BitmapEditor.rotate(bitmap, 90f)

        assert(rotatedBitmap.width == height && rotatedBitmap.height == width)
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
