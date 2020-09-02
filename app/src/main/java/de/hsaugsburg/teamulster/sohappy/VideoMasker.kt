package de.hsaugsburg.teamulster.sohappy

import jp.co.cyberagent.android.gpuimage.GPUImageView
import jp.co.cyberagent.android.gpuimage.filter.*

class VideoMasker(private val gpuImageView: GPUImageView) {

    private val filterBlue: GPUImageFilter = GPUImageFilterGroup(
        listOf(
            GPUImagePixelationFilter().apply { setPixel(5F) },
            GPUImageGaussianBlurFilter(5.0f),
            GPUImageContrastFilter(1f),
            GPUImageSepiaToneFilter(),
            GPUImageHueFilter(210f),
            GPUImageSaturationFilter(5f),
            GPUImageBrightnessFilter(-0.2f)
        )
    )
    private val filterRed: GPUImageFilter = GPUImageFilterGroup(
        listOf(
            GPUImagePixelationFilter().apply { setPixel(5F) },
            GPUImageGaussianBlurFilter(5.0f),
            GPUImageContrastFilter(1f),
            GPUImageSepiaToneFilter(),
            GPUImageHueFilter(330f),
            GPUImageSaturationFilter(3f),
            GPUImageBrightnessFilter(-0.2f)
        )
    )

    fun switchColor(face: Boolean) {
        if (face) {
            gpuImageView.filter = filterBlue
        } else {
            gpuImageView.filter = filterRed
        }
    }

}