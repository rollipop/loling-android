package mashup.loling

import android.content.Context
import android.graphics.Point
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class PagerContainer : FrameLayout, ViewPager.OnPageChangeListener {
    var viewPager: ViewPager? = null
    var mNeedsRedrow = false

    private val mCrenter = Point()
    private val mInitialTouch = Point()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        clipChildren = false
        setLayerType(View.LAYER_TYPE_SOFTWARE,null)
    }
    override fun onPageScrollStateChanged(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageSelected(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}