package com.yanfangxiong.multipagerdemo

import android.support.v4.view.ViewPager
import android.view.View

/**
 * @author fxYan
 */
class AlphaPageTransformer : ViewPager.PageTransformer {

    companion object {
        const val DEFAULT_MIN_ALPHA: Float = 0.5f
    }

    private var minAlpha: Float = DEFAULT_MIN_ALPHA

    fun setMinAlpha(alphaConfig: Float) {
        minAlpha = alphaConfig
    }

    override fun transformPage(page: View?, position: Float) {
        val alpha: Float = when {
            position < -1 -> minAlpha
            position >= -1 && position < 0 -> minAlpha + (1 - minAlpha) * (1 + position)
            position >= 0 && position < 1 -> minAlpha + (1 - minAlpha) * (1 - position)
            else -> minAlpha
        }
        page?.alpha = alpha
    }

}