package de.hsaugsburg.teamulster.sohappy.analyzer

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import android.widget.TextView
import de.hsaugsburg.teamulster.sohappy.CameraActivity
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.FaceDetector
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import de.hsaugsburg.teamulster.sohappy.config.ImageAnalyzerConfig
import kotlinx.android.synthetic.main.activity_camera.view.*
import kotlin.concurrent.thread

class ImageAnalyzer (val activity: CameraActivity, config: ImageAnalyzerConfig) {
    var faceDetector: FaceDetector? = (config.faceDetector?.getConstructor(Activity::class.java)
        ?.newInstance(activity))
    var smileDetector: SmileDetector? = (config.smileDetector?.getConstructor(Activity::class.java)
        ?.newInstance(activity))

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
        var croppedOutFace = BitmapEditor.crop(img, faceDR.frame)!!
        var smileDR = smileDetector!!.detect(croppedOutFace)
        return smileDR
    }

    fun execute() {
        // TODO: Improve this
        thread {
            while (true) {
                val bitmap = this.activity.queue.poll()
                if (bitmap != null) {
                    val result = computeSmileDetectionResult(bitmap)
                    val tv = activity.findViewById<TextView>(R.id.textView)
                    if (result != null) {
                        tv.text = if (result.isSmiling) "isSmiling"  else "noSmile"
                    } else {
                        tv.text = "No Face"
                    }
                    Log.d("Result:", result.toString())
                }
            }
        }
    }
}
