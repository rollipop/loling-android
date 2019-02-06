package mashup.loling

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.support.annotation.Px
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_main.*

class PagerContainer : FrameLayout, ViewPager.OnPageChangeListener {
    var viewPager: ViewPager? = null
    var mNeedsRedrow = false
    private val mCrenter = Point()
    private val mInitialTouch = Point()
    private var mContext: Context? = null

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
        mContext = context
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    @SuppressLint("MissingSuperCall")
    override fun onFinishInflate() {
        try {
            viewPager = getChildAt(0) as ViewPager
            viewPager!!.setOnPageChangeListener(this)
        } catch (e: Exception) {
            throw IllegalStateException("PagerContainer의 하위경로가 ViewPager여야 함")
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mCrenter.x = w / 2
        mCrenter.y = h / 2
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //ViewPager에서 아직 처리되지 않은 모든 터치
        // 호출 범위를 벗어난 터치에서 스크롤 실행.
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mInitialTouch.x = event.x.toInt()
                mInitialTouch.y = event.y.toInt()
                event.offsetLocation((mCrenter.x - mInitialTouch.x).toFloat(),
                        (mCrenter.y - mInitialTouch.y).toFloat())
            }
            else -> event.offsetLocation((mCrenter.x - mInitialTouch.x).toFloat(),
                    (mCrenter.y - mInitialTouch.y).toFloat())
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