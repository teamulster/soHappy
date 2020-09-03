package de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.media.Image
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.util.YuvToRgbConverter
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream

class HaarCascadeFaceDetector(private val activity: Activity) : FaceDetector {

    companion object {
        const val assetFilename = "haarcascade_frontalface_default.xml"
    }

    var haarCascade: CascadeClassifier
    override val detectorName = "HaarCascadeFaceDetector"

    init {
        OpenCVLoader.initDebug()
        haarCascade = setupClassifier()
    }

    private fun setupClassifier(): CascadeClassifier {
        val inputStream = activity.assets.open(Companion.assetFilename)

        val cascadeDir = activity.cacheDir;
        val mCascadeFile = File.createTempFile(Companion.assetFilename, "xml", cascadeDir)
        val os = FileOutputStream(mCascadeFile)

        val buffer = ByteArray(4096)
        var bytesRead: Int = 0
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            os.write(buffer, 0, bytesRead)
        }
        inputStream.close()
        os.close()

        return CascadeClassifier(mCascadeFile.absolutePath)
    }

    //
    private fun allocateBitmap(width: Int, height: Int): Bitmap {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }

    override fun detect(img: Bitmap): FaceDetector.Companion.FaceDetectionResult? {
        val converter = YuvToRgbConverter(activity)
        val mat = Mat()
        Utils.bitmapToMat(img, mat)

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY)
        val detectMat = MatOfRect()
        haarCascade.detectMultiScale(mat, detectMat, 1.3, 6)
        val list = detectMat.toList()
        if (list.size < 1) {
            return null
        }
        val firstFace = list[0]

        return FaceDetector.Companion.FaceDetectionResult(
            Rect(firstFace.x,
                firstFace.y,
                firstFace.x + firstFace.width,
                firstFace.y + firstFace.height)
        )
    }

}
