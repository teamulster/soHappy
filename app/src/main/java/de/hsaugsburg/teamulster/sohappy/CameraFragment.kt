package de.hsaugsburg.teamulster.sohappy

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import de.hsaugsburg.teamulster.sohappy.databinding.FragmentCameraBinding
import de.hsaugsburg.teamulster.sohappy.util.YuvToRgbConverter
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.GPUImageView
import java.util.concurrent.Executors


class CameraFragment: Fragment() {
    companion object {
        const val REQUEST_CODE_PERMISSIONS = 10
    }

    private val executor = Executors.newSingleThreadExecutor()
    private var cameraProvider: ProcessCameraProvider? = null
    private var bitmap: Bitmap? = null
    private lateinit var binding: FragmentCameraBinding
    private lateinit var converter: YuvToRgbConverter
    private lateinit var gpuImageView: GPUImageView

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

        converter = YuvToRgbConverter(requireContext())
        gpuImageView = binding.gpuImageView
        gpuImageView.setScaleType(GPUImage.ScaleType.CENTER_CROP)

        requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_PERMISSIONS)
        // From the processCameraProvider, we can request a Future, which will contain the
        // camera instance, when its available
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()
            startCameraIfReady()
        }, ContextCompat.getMainExecutor(context))

        return binding.root
    }

    override fun onStop() {
        super.onStop()

        cameraProvider!!.unbindAll()
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    @Suppress("MagicNumber")
    private fun startCameraIfReady() {
        // We need to build an ImageAnalysis, which will get binded to the cameraProvider.
        // We can register an Analyzer for the analysis, which is a callback.
        // This callback will convert the image provided by the analyzer to an Bitmap, and  will
        // send the image onto the gpuImageView
        // Then, we can use the bitmap for further processing
        if (isPermissionsGranted() && cameraProvider == null) {
            return
        }

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            // TODO: take a look into .setBackgroundExecutor()
            .build()

        imageAnalysis.setAnalyzer(executor, ImageAnalysis.Analyzer {
            var bitmap = allocateBitmapIfNecessary(it.width, it.height)
            //TODO: SuppressLint is dependent on it.image!! Why?
            converter.yuvToRgb(it.image!!, bitmap)

            // This code does rotate the bitmap
            // TODO: Move this to the ImageEditor
            val matrix = Matrix()
            matrix.postRotate(-90F)
            val scaledBitmap = Bitmap.createScaledBitmap(
                bitmap, bitmap.width, bitmap.height, true
            )
            bitmap = Bitmap.createBitmap(
                scaledBitmap, 0, 0,  scaledBitmap.width, scaledBitmap.height, matrix, true
            )

            gpuImageView.post {
                gpuImageView.setRatio((bitmap.width/ bitmap.height).toFloat())
                Log.d("Min Height", gpuImageView.height.toString())
                Log.d("Bitmap width", bitmap.width.toString());
                Log.d("Bitmap height", bitmap.height.toString())
                gpuImageView.setImage(bitmap)
            }
            it.close()
        })

        cameraProvider!!.bindToLifecycle(this, CameraSelector.DEFAULT_FRONT_CAMERA, imageAnalysis)
    }

    private fun allocateBitmapIfNecessary(width: Int, height: Int): Bitmap {
        if (bitmap == null || bitmap!!.width != width || bitmap!!.height != height) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        }

        return bitmap!!
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            startCameraIfReady()
        }
    }

    private fun isPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
}
