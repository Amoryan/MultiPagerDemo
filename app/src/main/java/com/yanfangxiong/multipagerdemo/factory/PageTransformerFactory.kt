package com.yanfangxiong.multipagerdemo.factory

import android.support.v4.view.ViewPager

/**
 * @author fxYan
 */
interface PageTransformerFactory {

    fun generatePageTransformer(): ViewPager.PageTransformer

}