# 前言
　　最近项目UI图上有个一屏显示多个Pager的控件，想了想直接通过ViewPager来实现。
# 实现方式
## getPageWidth
　　实际上PagerAdapter里面就提供了实现一屏多显的方法
```java
/**
 * Returns the proportional width of a given page as a percentage of the
 * ViewPager's measured width from (0.f-1.f]
 *
 * @param position The position of the page requested
 * @return Proportional width for the given page position
 */
public float getPageWidth(int position) {
    return 1.f;
}
```
　　这个方法返回的是 **<font color="#1b8fe6">每个Pager的宽度</font>** 。然而，他的效果并不是我们想要的。来看看下面这段代码的运行效果。
```kotlin
override fun getPageWidth(position: Int): Float {
    return 0.8f
}
```
　　它的效果如下所示，可以看到，默认每个pager都是 **<font color="#1b8fe6">局左显示</font>** 的。<br />
![getPageWidth()](/images/getPageWidth.gif)
## clipChildren
　　第二种实现方式就是通过控件的 **<font color="#1b8fe6">clipChildren</font>** 属性，默认这个属性是true，我们需要将其设置为false，表示超出控件的内容范围也要显示出来。
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#bbbbbb"
    android:clipChildren="false"
    android:gravity="center"
    android:orientation="vertical"
    tools:context="com.yanfangxiong.multipagerdemo.MainActivity">

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="180dp"
        android:layout_marginLeft="20dp"
        android:layout_marginRight="20dp"
        android:clipChildren="false"
        android:overScrollMode="never"/>

</LinearLayout>
```
　　这里将ViewPager和LinearLayout的clipChildren属性都设置为了false。然后在java代码中做如下设置。
```kotlin
//设置预加载的数量是3，这个值默认是1
viewPager.offscreenPageLimit = 3
//pageMargin设置页面之间的距离
val metrics = DisplayMetrics()
windowManager.defaultDisplay.getMetrics(metrics)
viewPager.pageMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, metrics).toInt()
```
　　效果图如下所示。<br />
![result](/images/result.gif)
# 扩展
　　感觉好像有点单调哈。我也是这么觉得的，那么我们来加点有趣的东西吧。ViewPager有个PageTransformer接口。
```java
public interface PageTransformer {
    /**
     * Apply a property transformation to the given page.
     *
     * @param page Apply the transformation to this page
     * @param position Position of page relative to the current front-and-center
     *                 position of the pager. 0 is front and center. 1 is one full
     *                 page position to the right, and -1 is one page position to the left.
     */
    void transformPage(View page, float position);
}
```
　　这里我就不写研究过程了，position的范围可以分为四段(通用的)，是当前page的左上角相对于ViewPager的位置。
1. position < -1
2. -1 <= position < 0
3. 0 <= position < 1
4. position > 1
## ScalePageTransformer
　　于是乎我写了一个切换尺寸变换的类ScalePageTransformer，如下。
```kotlin
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
```
　　效果图如下，这样就比较有趣了，嗯，我是这么认为的。<br />
![scaleResult](/images/scaleResult.gif)
## RotatePageTransformer
　　旋转跳跃，我闭着眼~
```kotlin
class RotatePageTransformer : ViewPager.PageTransformer {

    companion object {
        const val DEFAULT_ROTATE_DEGREE: Float = 10f
    }

    private var rotateDegree: Float = DEFAULT_ROTATE_DEGREE

    fun setRotateDegree(degree: Float) {
        this.rotateDegree = degree
    }

    override fun transformPage(page: View?, position: Float) {
        if (page == null) return

        val tPivotX: Float
        val degree: Float
        when {
            position < -1 -> {
                tPivotX = page.width.toFloat()
                degree = -rotateDegree
            }
            position >= -1 && position < 0 -> {
                tPivotX = page.width.toFloat()
                degree = rotateDegree * position
            }
            position >= 0 && position < 1 -> {
                tPivotX = 0f
                degree = rotateDegree * position
            }
            else -> {
                tPivotX = 0f
                degree = rotateDegree
            }
        }
        page.apply {
            pivotX = tPivotX
            pivotY = if (rotateDegree < 0) 0f else page.height.toFloat()
            rotation = degree
        }
    }

}
```
　　来看看这个旋转变换的效果图。<br />
![rotateResult](/images/rotateResult.gif)
## AlphaPageTransformer
　　再来个透明度变化的吧。
```kotlin
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
```
　　效果图如下，日子还长，别太失望~
![alphaResult](/images/alphaResult.gif)
