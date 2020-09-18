package de.hsaugsburg.teamulster.sohappy

import jp.co.cyberagent.android.gpuimage.GPUImageView
import jp.co.cyberagent.android.gpuimage.filter.*

/**
 * Helper class to blur the camera image with a specific color.
 */
object VideoMasker {

    lateinit var gpuImageView: GPUImageView
    /**
     * applies blue blur filter.
     */
    fun applyBlueFilter() {
        gpuImageView.filter = GPUImageFilterGroup(
            listOf(
                GPUImagePixelationFilter().apply { setPixel(5F) },
                GPUImageGaussianBlurFilter(5.0f),
                GPUImageContrastFilter(1f),
                GPUImageSepiaToneFilter(),
                GPUImageHueFilter(210f),
                GPUImageSaturationFilter(1f),
                GPUImageBrightnessFilter(-0.2f)
            )
        )
    }

    /**
     * applies red blur filter.
     */
    fun applyRedFilter() {
        gpuImageView.filter = GPUImageFilterGroup(
            listOf(
                GPUImagePixelationFilter().apply { setPixel(5F) },
                GPUImageGaussianBlurFilter(5.0f),
                GPUImageContrastFilter(1f),
                GPUImageSepiaToneFilter(),
                GPUImageHueFilter(330f),
                GPUImageSaturationFilter(1f),
                GPUImageBrightnessFilter(-0.2f)
            )
        )
    }

    /**
     * applies yellow blur filter.
     */
    fun applyYellowFilter() {
        gpuImageView.filter = GPUImageFilterGroup(
            listOf(
                GPUImagePixelationFilter().apply { setPixel(5F) },
                GPUImageGaussianBlurFilter(5.0f),
                GPUImageContrastFilter(1f),
                GPUImageSepiaToneFilter(),
                GPUImageHueFilter(30f),
                GPUImageSaturationFilter(1f),
                GPUImageBrightnessFilter(-0.2f)
            )
        )
    }

    /**
     * applies green blur filter.
     */
    fun applyGreenFilter() {
        gpuImageView.filter = GPUImageFilterGroup(
            listOf(
                GPUImagePixelationFilter().apply { setPixel(5F) },
                GPUImageGaussianBlurFilter(5.0f),
                GPUImageContrastFilter(1f),
                GPUImageSepiaToneFilter(),
                GPUImageHueFilter(90f),
                GPUImageSaturationFilter(1f),
                GPUImageBrightnessFilter(-0.2f)
            )
        )
    }
}
