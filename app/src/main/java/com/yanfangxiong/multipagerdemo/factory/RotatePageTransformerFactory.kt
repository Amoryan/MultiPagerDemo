package com.yanfangxiong.multipagerdemo.factory

import android.support.v4.view.ViewPager
import com.yanfangxiong.multipagerdemo.tranformer.RotatePageTransformer

/**
 * @author fxYan
 */
class RotatePageTransformerFactory : PageTransformerFactory {

    override fun generatePageTransformer(): ViewPager.PageTransformer {
        val defaultDegree = 10f
        return RotatePageTransformer(defaultDegree)
    }

}