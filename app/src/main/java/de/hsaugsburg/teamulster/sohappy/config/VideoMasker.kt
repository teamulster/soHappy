package de.hsaugsburg.teamulster.sohappy.config

import android.media.Image
import jp.co.cyberagent.android.gpuimage.filter.*

class VideoMasker(image: Image) {

    val filterBlue: GPUImageFilter = GPUImageFilterGroup(listOf(GPUImageGaussianBlurFilter(20.0f),
        GPUImageContrastFilter(1f), GPUImageSepiaToneFilter(), GPUImageHueFilter(210f),
        GPUImageSaturationFilter(5f), GPUImageBrightnessFilter(-0.2f)))
    val filterRed: GPUImageFilter =  GPUImageFilterGroup(listOf(GPUImageGaussianBlurFilter(20.0f),
        GPUImageSepiaToneFilter(), GPUImageHueFilter(330f), GPUImageSaturationFilter(5f),
        GPUImageBrightnessFilter(-0.2f)))

}