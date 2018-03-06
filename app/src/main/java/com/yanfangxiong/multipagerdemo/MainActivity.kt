package com.yanfangxiong.multipagerdemo

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val MIN_SIZE = 0.8f
    }

    private val views: ArrayList<View> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstView = LayoutInflater.from(this).inflate(R.layout.pageritem, null)
        views.add(firstView)
        views.add(LayoutInflater.from(this).inflate(R.layout.pageritem, null))
        views.add(LayoutInflater.from(this).inflate(R.layout.pageritem, null))

        viewPager.offscreenPageLimit = 3
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        viewPager.pageMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, metrics).toInt()
        viewPager.apply {
            adapter = object : PagerAdapter() {

                override fun isViewFromObject(view: View?, `object`: Any?): Boolean = view == `object`

                override fun getCount(): Int = views.size

                override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                    val view = views[position]
                    container?.addView(view)
                    return view
                }

                override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                    container?.removeView(views[position])
                }

            }
        }
        viewPager.setPageTransformer(false, AlphaPageTransformer())
    }
}
