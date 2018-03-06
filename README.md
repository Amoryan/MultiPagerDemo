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
　　它的效果如下所示，可以看到，默认每个pager都是 **<font color="#1b8fe6">局左显示</font>** 的。
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
　　效果图如下所示。
![result](/images/result.gif)
