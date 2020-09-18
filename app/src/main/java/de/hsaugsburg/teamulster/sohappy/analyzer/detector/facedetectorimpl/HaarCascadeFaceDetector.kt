package de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Rect
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.facedetectorimpl.HaarCascadeFaceDetector.Companion.assetFilename
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream

/**
 * This class implements Haar Cascade FaceDetector approach. It implements the FaceDetector interface.
 *
 * @constructor takes the current activity and returns a new HaarCascadeFaceDetector object
 * @property haarCascade
 * @property detectorName
 * @property assetFilename
 */
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
        val inputStream = activity.assets.open(assetFilename)

        val cascadeDir = activity.cacheDir
        val mCascadeFile = File.createTempFile(assetFilename, "xml", cascadeDir)
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

    @Suppress("UnusedPrivateMember")
    override fun detect(img: Bitmap): FaceDetector.Companion.FaceDetectionResult? {
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

        detectMat.release()
        mat.release()
        return FaceDetector.Companion.FaceDetectionResult(
            Rect(
                firstFace.x,
                firstFace.y,
                firstFace.x + firstFace.width,
                firstFace.y + firstFace.height
            )
        )
    }
}
