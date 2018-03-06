package com.yanfangxiong.multipagerdemo.factory

import android.support.v4.view.ViewPager
import com.yanfangxiong.multipagerdemo.tranformer.AlphaPageTransformer

/**
 * @author fxYan
 */
class AlphaPageTransformerFactory : PageTransformerFactory {

    override fun generatePageTransformer(): ViewPager.PageTransformer {
        return AlphaPageTransformer()
    }

}