package com.yanfangxiong.multipagerdemo.factory

import android.support.v4.view.ViewPager
import com.yanfangxiong.multipagerdemo.tranformer.ScalePageTransformer

/**
 * @author fxYan
 */
class ScalePageTransformerFactory : PageTransformerFactory {

    override fun generatePageTransformer(): ViewPager.PageTransformer {
        return ScalePageTransformer()
    }

}