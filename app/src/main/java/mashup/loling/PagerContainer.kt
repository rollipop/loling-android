package mashup.loling

import android.content.Context
import android.graphics.Point
import android.support.annotation.Px
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

class PagerContainer : FrameLayout, ViewPager.OnPageChangeListener {
    var viewPager: ViewPager? = null
    var mNeedsRedrow = false
    private val mCenter = Point()
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
        //다음에 나올 뷰를 미리 보이게함
        clipChildren = false
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun onFinishInflate() {
        try {
            viewPager = getChildAt(0) as ViewPager
            viewPager!!.setOnPageChangeListener(this)
        } catch (e: Exception) {
            throw IllegalStateException("The root child of PagerContainer must be a ViewPager")
        }
        return super.onFinishInflate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mCenter.x = w / 2
        mCenter.y = h / 2
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //ViewPager에서 아직 처리되지 않은 모든 터치
        // 호출 범위를 벗어난 터치에서 스크롤 실행.
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mInitialTouch.x = event.x.toInt()
                mInitialTouch.y = event.y.toInt()
                event.offsetLocation((mCenter.x - mInitialTouch.x).toFloat(),
                        (mCenter.y - mInitialTouch.y).toFloat())
            }
            else -> event.offsetLocation((mCenter.x - mInitialTouch.x).toFloat(),
                    (mCenter.y - mInitialTouch.y).toFloat())
        }

        return viewPager!!.dispatchTouchEvent(event)
    }

    override fun onPageSelected(p0: Int) {
    }

    override fun onPageScrolled(position: Int, p1: Float, @Px p2: Int) {}

    override fun onPageScrollStateChanged(state: Int) {
        mNeedsRedrow = (state != ViewPager.SCROLL_STATE_IDLE)
    }
}