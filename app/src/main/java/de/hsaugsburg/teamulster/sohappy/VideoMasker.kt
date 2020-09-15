package de.hsaugsburg.teamulster.sohappy

import jp.co.cyberagent.android.gpuimage.GPUImageView
import jp.co.cyberagent.android.gpuimage.filter.*

/**
 * Helper class to blur the camera image with a specific color.
 */
object VideoMasker {

    private val blueFilter: GPUImageFilter = GPUImageFilterGroup(
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
    private val redFilter: GPUImageFilter = GPUImageFilterGroup(
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
    private val yellowFilter: GPUImageFilter = GPUImageFilterGroup(
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
    private val greenFilter: GPUImageFilter = GPUImageFilterGroup(
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

    /**
     * applies blue blur filter.
     *
     * @param gpuImageView the image to be blurred.
     */
    fun applyBlueFilter (gpuImageView: GPUImageView): GPUImageView = gpuImageView.apply {
        gpuImage.setFilter(blueFilter)
    }

    /**
     * applies red blur filter.
     *
     * @param gpuImageView the image to be blurred.
     */
    fun applyRedFilter (gpuImageView: GPUImageView): GPUImageView = gpuImageView.apply {
        gpuImage.setFilter(redFilter)
    }

    /**
     * applies yellow blur filter.
     *
     * @param gpuImageView the image to be blurred.
     */
    fun applyYellowFilter (gpuImageView: GPUImageView): GPUImageView = gpuImageView.apply {
        gpuImage.setFilter(yellowFilter)
    }

    /**
     * applies green blur filter.
     *
     * @param gpuImageView the image to be blurred.
     */
    fun applyGreenFilter (gpuImageView: GPUImageView): GPUImageView = gpuImageView.apply {
        gpuImage.setFilter(greenFilter)
    }
}
