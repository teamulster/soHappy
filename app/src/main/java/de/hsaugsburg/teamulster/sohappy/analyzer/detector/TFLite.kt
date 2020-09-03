package de.hsaugsburg.teamulster.sohappy.analyzer.detector

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.RectF
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.util.*
import kotlin.collections.ArrayList


abstract class TFLite(tfliteModelPath: String, numberOfThreads: Int, activity: Activity) :
    SmileDetector {
    companion object {
        // Class for storing prediction results
        class Recognition(
            val id: String, val title: String, val confidence: Float,
            private val location: RectF?
        )

        // Override SmileDetectionResult to store predictionResults if isSmiling == false
        class SmileDetectionResult(
            isSmiling: Boolean,
            val predictionResults: ArrayList<Recognition>
        ) :
            SmileDetector.Companion.SmileDetectionResult(isSmiling)
    }

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

    // run prediction
    // TODO: Choose whether model is quantized or not / probabilityProcessor is necessary
    fun execute(img: Bitmap): ArrayList<Recognition> {
        // Init probability val
        val probabilityTensorIndex: Int = 0
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
        tfliteInterpreter.run(prepare(img), outputProbability.buffer.rewind())

        // Post process probability and return top matches
        val labeledProbability: Map<String, Float> =
            TensorLabel(labels, probabilityProcessor.process(outputProbability))
                .mapWithFloatValue
        return getTopMatches(labeledProbability);
    }

    // Return ordered list with top 20 matches in processed image
    private fun getTopMatches(labeledProbability: Map<String, Float>): ArrayList<Recognition> {
        val maxResults: Int = 20
        // Find the best classifications.
        val pq: PriorityQueue<Recognition> = PriorityQueue(maxResults)
        // Intentionally reversed to put high confidence at the head of the queue.
        { lhs, rhs ->
            rhs.confidence.compareTo(lhs.confidence)
        }

        for ((key, value) in labeledProbability.entries) {
            pq.add(Recognition("" + key, key, value, null))
        }

        val recognitions: ArrayList<Recognition> = ArrayList()
        val recognitionsSize: Int = pq.size.coerceAtMost(maxResults)
        for (i in 0 until recognitionsSize) {
            recognitions.add(pq.poll())
        }
        return recognitions
    }

    abstract fun prepare(img: Bitmap): ByteBuffer?
}
