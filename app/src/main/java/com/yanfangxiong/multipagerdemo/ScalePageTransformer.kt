package com.yanfangxiong.multipagerdemo

import android.support.v4.view.ViewPager
import android.view.View

/**
 * @author fxYan
 */
class ScalePageTransformer : ViewPager.PageTransformer {

    companion object {
        const val DEFAULT_MIN_SCALE = 0.8F
    }

    private var minScale = DEFAULT_MIN_SCALE

    fun setMinScale(scale: Float) {
        this.minScale = scale
    }

    override fun transformPage(page: View?, position: Float) {
        val size = when {
            position < -1 -> minScale
            position >= -1 && position < 0 -> minScale + (1 - minScale) * (1 + position)
            position < 1 -> minScale + (1 - minScale) * (1 - position)
            else -> minScale
        }
        page?.scaleY = size
    }

}