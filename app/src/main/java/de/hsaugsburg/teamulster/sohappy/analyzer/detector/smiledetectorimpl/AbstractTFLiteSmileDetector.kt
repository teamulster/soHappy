package de.hsaugsburg.teamulster.sohappy.analyzer.detector.smiledetectorimpl

import android.app.Activity
import android.graphics.Bitmap
import de.hsaugsburg.teamulster.sohappy.analyzer.detector.SmileDetector
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer

/**
 * This abstract class inherits SmileDetector. It declares function necessary for TF lite to be run.
 *
 * @param tfliteModelPath the path where the tf lite model is stored
 * @param numberOfThreads the number of threads which will be used when tf lite is run
 * @param activity the current activity this class was invoked by (e.g. CameraActivity)
 * @constructor creates a TFLite object while setting tfliteOptions/tfliteModel/tfliteInterpreter
 * */
abstract class AbstractTFLiteSmileDetector(
    tfliteModelPath: String,
    activity: Activity,
    numberOfThreads: Int = 1
) : SmileDetector {
    // Static classes
    companion object {

        /**
         * This data class inherits the SmileDetector.Companion.SmileDetectionResult(isSmiling) function
         * and overrides it.
         *
         * @param [isSmiling] determines whether the image contains a smiling person or not
         * @param [predictionResults] stores all top matches
         * @constructor creates SmileDetectionResult object containing the given params
         * */
        data class SmileDetectionResult(
            override val isSmiling: Boolean,
            override val predictionResults: ArrayList<SmileDetector.Companion.Recognition>
        ) :
            SmileDetector.Companion.SmileDetectionResult(isSmiling, predictionResults)
    }

    // Init variables
    override val detectorName = "TFLite"
    val labels: ArrayList<String> = ArrayList()
    private val tfliteOptions: Interpreter.Options = Interpreter.Options()
    private val tfliteModel: MappedByteBuffer
    private val tfliteInterpreter: Interpreter

    init {
        tfliteOptions.setNumThreads(numberOfThreads)
        tfliteModel = FileUtil.loadMappedFile(activity, tfliteModelPath)
        tfliteInterpreter = Interpreter(tfliteModel, tfliteOptions)
    }

    /**
     * This function runs tfliteInterpreter detection on a given image and predicts its probability to match
     * one of the model's labels.
     *
     * @param [img] the current image
     * @return [ArrayList]<Recognition>
     * */
    fun execute(img: Bitmap): ArrayList<SmileDetector.Companion.Recognition> {
        // Init probability val
        val probabilityTensorIndex = 0
        val probabilityShape: IntArray =
            tfliteInterpreter.getOutputTensor(probabilityTensorIndex).shape()
        val probabilityDataType: DataType =
            tfliteInterpreter.getOutputTensor(probabilityTensorIndex).dataType()
        val outputProbability: TensorBuffer =
            TensorBuffer.createFixedSize(
                probabilityShape,
                probabilityDataType
            )
        val probabilityProcessor: TensorProcessor =
            TensorProcessor.Builder().build()

        // Run the inference call / Predict image
        val preparedImg = prepare(img)
        val outputProbabilityBuffer = outputProbability.buffer.rewind()
        tfliteInterpreter.run(preparedImg, outputProbabilityBuffer)

        // Post process probability and return top matches
        val labeledProbability: Map<String, Float> =
            TensorLabel(labels, probabilityProcessor.process(outputProbability))
                .mapWithFloatValue
        return sortMatches(labeledProbability)
    }

    /**
     * This private function sorts the prediction result by confidence of matching.
     *
     * @param [labeledProbability] a map containing the confidence mapped on predicted labels
     * @return [ArrayList]<Recognition>
     * */
    private fun sortMatches(labeledProbability: Map<String, Float>): ArrayList<SmileDetector.Companion.Recognition> {
        val recognitions: ArrayList<SmileDetector.Companion.Recognition> = ArrayList()

        for ((key, value) in labeledProbability.entries) {
            recognitions.add(SmileDetector.Companion.Recognition("" + key, value))
        }
        // Intentionally reversed to put high confidence at the head of the queue.
        recognitions.sortWith { lhs, rhs -> rhs.confidence.compareTo(lhs.confidence) }
        return recognitions
    }

    /**
     * This abstract function is implemented in FerTFLiteSmileDetectorImpl. It is used to invoke this
     * classes execute function.
     *
     * @param [img] the current image
     * @return [ByteBuffer]
     * */
    abstract fun prepare(img: Bitmap): ByteBuffer?
}
