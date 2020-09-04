package de.hsaugsburg.teamulster.sohappy.analyzer

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import com.glidebitmappool.GlideBitmapPool
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import de.hsaugsburg.teamulster.sohappy.config.ImageAnalyzerConfig
import kotlin.concurrent.thread

class ImageAnalyzer (val activity: CameraActivity, config: ImageAnalyzerConfig) {
    var faceDetector: FaceDetector? = (config.faceDetector?.getConstructor(Activity::class.java)
        ?.newInstance(activity))
    var smileDetector: SmileDetector? = null //config.smileDetector?.newInstance() as SmileDetector

    @Suppress("FunctionOnlyReturningConstant")
    fun computeFaceDetectionResult(img: Bitmap): FaceDetector.Companion.FaceDetectionResult? {
        return faceDetector?.detect(img)
    }

    @Suppress("FunctionOnlyReturningConstant")
    fun computeSmileDetectionResult(img: Bitmap): SmileDetector.Companion.SmileDetectionResult? {
        val faceDR = faceDetector?.detect(img)
        if (faceDR == null) {
            //fixme: do we want to throw an exception here?
            return null;
        }
        var croppedOutFace = ImageEditor.crop(img, faceDR.frame)!!
        var smileDR = smileDetector!!.detect(croppedOutFace)
        return smileDR
    }

    fun execute() {
        // TODO: Improve this
        thread {
            while (true) {
                val bitmap = this.activity.queue.poll()
                if (bitmap != null) {
                    val result = computeFaceDetectionResult(bitmap)
                    Log.d("Result:", result?.frame.toString())
                    GlideBitmapPool.putBitmap(bitmap)
                }
            }
        }
    }
}
