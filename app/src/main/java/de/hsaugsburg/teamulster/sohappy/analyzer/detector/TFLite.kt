package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.app.Activity
import android.graphics.Bitmap
import android.media.Image
import de.hsaugsburg.teamulster.sohappy.util.YuvtoRgbConverter
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.MappedByteBuffer


abstract class TFLite(tfliteModelPath: String, numberOfThreads: Int, activity: Activity) : SmileDetector {
    override val detectorName = "TFLite"

    var processedImg: Image

    private val yuvtoRgbConverter: YuvtoRgbConverter = YuvtoRgbConverter(activity)
    private val tfliteOptions: Interpreter.Options = Interpreter.Options()
    private val tfliteModel: MappedByteBuffer
    private val tfliteInterpreter: Interpreter
    private val outputProbabilityBuffer: TensorBuffer? = null
    private val probabilityProcessor: TensorProcessor? =

    init {
        tfliteOptions.setNumThreads(numberOfThreads)
        tfliteModel = FileUtil.loadMappedFile(activity, tfliteModelPath)
        tfliteInterpreter = Interpreter(tfliteModel, tfliteOptions)
    }

    fun execute(img: Image) {
        tfliteInterpreter.run(prepare(img).tensorBuffer)
    }

    private fun prepare(img: Image) : TensorImage {
        val bitmapImg: Bitmap = Bitmap.createBitmap(img.width, img.height, Bitmap.Config.ARGB_8888)
        val imageDataType: DataType = tfliteInterpreter.getInputTensor(0).dataType()
        val imgProcessor: ImageProcessor = ImageProcessor.Builder().build()
        val returnImage = TensorImage(imageDataType)
        yuvtoRgbConverter.yuvToRgb(img, bitmapImg)
        returnImage.load(bitmapImg)
        return imgProcessor.process(returnImage)
    }
}
