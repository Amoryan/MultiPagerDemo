package com.yanfangxiong.multipagerdemo.tranformer

import android.support.v4.view.ViewPager
import android.view.View

/**
 * @author fxYan
 */
class ScalePageTransformer(
        private var minScale: Float
) : ViewPager.PageTransformer {

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