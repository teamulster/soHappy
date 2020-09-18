package de.hsaugsburg.teamulster.sohappy.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import de.hsaugsburg.teamulster.sohappy.R
import de.hsaugsburg.teamulster.sohappy.VideoMasker
import de.hsaugsburg.teamulster.sohappy.analyzer.BitmapEditor
import de.hsaugsburg.teamulster.sohappy.analyzer.ImageAnalyzer
import de.hsaugsburg.teamulster.sohappy.config.ConfigManager
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentCameraBinding
import de.hsaugsburg.teamulster.sohappy.queue.BitmapQueue
import de.hsaugsburg.teamulster.sohappy.util.YuvToRgbConverter
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.GPUImageView
import java.util.concurrent.Executors

/**
 * CameraFragment uses the device's camera in order to perform various
 * image processing operations.
 */
class CameraFragment : Fragment() {
    companion object {
        const val REQUEST_CODE_PERMISSIONS = 10
    }

    private val executor = Executors.newSingleThreadExecutor()
    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraIsRunning = false
    private lateinit var binding: FragmentCameraBinding
    private lateinit var bitmap: Bitmap
    private lateinit var converter: YuvToRgbConverter
    private lateinit var gpuImageView: GPUImageView
    private lateinit var imageAnalyzer: ImageAnalyzer
    lateinit var queue: BitmapQueue

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_camera,
            container,
            false
        )
        val context = requireContext()

        converter = YuvToRgbConverter(context)
        gpuImageView = binding.gpuImageView
        gpuImageView.setScaleType(GPUImage.ScaleType.CENTER_CROP)
        VideoMasker.gpuImageView = gpuImageView
        queue = BitmapQueue()
        imageAnalyzer = ImageAnalyzer(this, ConfigManager.imageAnalyzerConfig)

        requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_PERMISSIONS)
        // From the processCameraProvider, we can request a Future, which will contain the
        // camera instance, when its available
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            startCameraIfReady()
        }, ContextCompat.getMainExecutor(context))

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        cameraProvider?.unbindAll()
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private fun startCameraIfReady() {
        // We need to build an ImageAnalysis, which will get binded to the cameraProvider.
        // We can register an Analyzer for the analysis, which is a callback.
        // This callback will convert the image provided by the analyzer to an Bitmap, and  will
        // send the image onto the gpuImageView
        // Then, we can use the bitmap for further processing
        if (!isPermissionsGranted() || cameraProvider == null) {
            return
        }
        if (cameraIsRunning) {
            return
        }
        cameraIsRunning = true
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            // TODO: take a look into .setBackgroundExecutor()
            .build()

        imageAnalysis.setAnalyzer(executor, {
            var bitmap = allocateBitmapIfNecessary(it.width, it.height)
            //TODO: SuppressLint is dependent on it.image!! Why?
            converter.yuvToRgb(it.image!!, bitmap)

            bitmap = BitmapEditor.rotate(bitmap, -90f)
            bitmap = BitmapEditor.flipHorizontal(bitmap)

            queue.replace(bitmap.copy(bitmap.config, false))

            gpuImageView.post {
                gpuImageView.setRatio((bitmap.width / bitmap.height).toFloat())
                gpuImageView.setImage(bitmap)

            }
            it.close()
        })


        cameraProvider!!.bindToLifecycle(this, CameraSelector.DEFAULT_FRONT_CAMERA, imageAnalysis)
        if (parentFragment is SmileFragment) {
            imageAnalyzer.execute()
        }
    }

    private fun allocateBitmapIfNecessary(width: Int, height: Int): Bitmap {
        if (!this::bitmap.isInitialized || bitmap.width != width || bitmap.height != height) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }
        return bitmap
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            //startCameraIfReady()
        }
    }

    private fun isPermissionsGranted(): Boolean = ContextCompat
        .checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
}
