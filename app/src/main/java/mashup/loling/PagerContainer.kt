package mashup.loling

import android.content.Context
import android.graphics.Point
import android.support.annotation.Px
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_author_list_detail.view.*
import mashup.loling.R.id.tvAuthorListDetailPage
import org.w3c.dom.Text

class PagerContainer : FrameLayout, ViewPager.OnPageChangeListener {
    var viewPager: ViewPager? = null
    private val mCenter = Point()
    private val mInitialTouch = Point()

    private var mIndicatorView: PagerIndicator? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        //다음에 나올 뷰를 미리 보이게함
        clipChildren = false
    }

    override fun onFinishInflate() {
        val maybeViewPager = getChildAt(0)
        if(maybeViewPager is ViewPager) {
            viewPager = maybeViewPager
            viewPager!!.clearOnPageChangeListeners()
            viewPager!!.addOnPageChangeListener(this)
        } else {
            throw IllegalStateException("The root child of PagerContainer must be a ViewPager")
        }
        return super.onFinishInflate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mCenter.x = w / 2
        mCenter.y = h / 2
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        /* ViewPager 외부에서(FrameLayout 안에서) 터치 및 드래그 했을 때에도
           ViewPager가 스와이핑 될 수 있도록 터치 이벤트 좌표를 옮겨주는 코드 */
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

    fun setIndicator(pagerIndicator: PagerIndicator) {
        this.mIndicatorView = pagerIndicator
    }

    override fun onPageSelected(p0: Int) {
        mIndicatorView?.selectDot(p0)

        val parentView = parent as View
        parentView.tvAuthorListDetailPage?.text = (p0+1).toString()


    }
    override fun onPageScrolled(position: Int, p1: Float, @Px p2: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
}