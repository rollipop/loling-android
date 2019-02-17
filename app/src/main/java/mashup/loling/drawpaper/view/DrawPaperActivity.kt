package mashup.loling.drawpaper.view

import android.graphics.Color
import android.graphics.PointF
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_draw_paper.*
import mashup.loling.R
import mashup.loling.drawpaper.Component

class DrawPaperActivity : AppCompatActivity(), IComponentTouchListener {
    override fun onComponentSelected(component: Component) {
        selectedComponent = component
    }

    private val componentList: ArrayList<Component> = ArrayList()
    private var selectedComponent: Component? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_paper)

        btnDrawPaperCamera.setOnClickListener { btnDrawPaperCameraClicked() }
        btnDrawPaperText.setOnClickListener { btnDrawPaperTextClicked() }

    }

    private fun btnDrawPaperCameraClicked() {
        // Todo : send implicit intent(camera or image)
    }

    private fun btnDrawPaperTextClicked() {
        val newComponent = Component(TextView(this), Component.Companion.ComponentType.TEXT, this)
        (newComponent.view as TextView).text = "TEST TEXT"
        (newComponent.view as TextView).textSize = 90f
        (newComponent.view as TextView).setBackgroundColor(Color.BLUE)
        addComponent(newComponent)

        Log.v(DrawPaperActivity::class.java.simpleName, "New Text Component Added!!")
    }

    private fun addComponent(newComponent: Component) {
        componentList.add(newComponent)
        drawArea.addView(newComponent.view)
    }

    // variables for position
    var originTouchPoint = PointF(0f, 0f)
    var originLeft = 0
    var originTop = 0
    // variables for scale
    var originDistance = 0f
    var originScale = 0f
    // variables for rotation
    var originDegree = 0f
    var originRotation = 0f
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev == null) return super.onTouchEvent(ev)

        var pointerCnt = ev.pointerCount
        pointerCnt = if(pointerCnt > 2) 2 else pointerCnt   // 최대 멀티터치는 두개까지만 제한
        Log.v(Component::javaClass.name, "Touc View" + pointerCnt)


        when(ev.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {    // 1개 터치 다운
                originTouchPoint.x = ev.getX(0)
                originTouchPoint.y = ev.getY(0)
                originDegree = 0f
                originRotation = 0f
                selectedComponent?.let {
                    val lParam: RelativeLayout.LayoutParams = it.view.layoutParams as RelativeLayout.LayoutParams
                    originLeft = lParam.leftMargin
                    originTop = lParam.topMargin
                    originScale = it.view.scaleX
                    originRotation = it.view.rotation
                }

            }
            MotionEvent.ACTION_POINTER_DOWN -> {    // 터치 다운
                if(pointerCnt == 2) {
                    originDistance = calculateDistance(ev)
                    originDegree = calculateDegree(ev)
                }
            }

            MotionEvent.ACTION_MOVE -> {    // 무-브
                selectedComponent?.let {
                    val lParam: RelativeLayout.LayoutParams = it.view.layoutParams as RelativeLayout.LayoutParams
                    val dMove = PointF(ev.getX(0), ev.getY(0))
                    dMove.offset(-originTouchPoint.x, -originTouchPoint.y)
                    var dDist = calculateDistance(ev) / originDistance
                    var dDeg = calculateDegree(ev) - originDegree

                    // move position
                    lParam.leftMargin = originLeft + dMove.x.toInt()
                    lParam.topMargin = originTop + dMove.y.toInt()
                    it.view.layoutParams = lParam

                    Log.v("JUJIN", "dist $originScale $dDist, deg $originRotation $dDeg")

                    // rotate/scale using matrix
                    if(dDist.isNaN() || dDist == 0f) dDist = 1f
                    it.scale(originScale * dDist)
                    it.rotate(originRotation + dDeg)
                }
            }

            MotionEvent.ACTION_POINTER_UP -> {  // 터치 업
                selectedComponent = null
            }
        }

        return super.onTouchEvent(ev)
    }

    private fun calculateDistance(ev: MotionEvent): Float {
        if(ev.pointerCount < 2) return 0f
        return calculateDistance(
                ev.getX(0), ev.getY(0),
                ev.getX(1), ev.getY(1))
    }
    private fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val dx = (x1 - x2).toDouble()
        val dy = (y1 - y2).toDouble()
        return Math.sqrt((dx*dx)+(dy*dy)).toFloat()
    }

    private fun calculateDegree(ev: MotionEvent): Float {
        if(ev.pointerCount < 2) return 0f
        return calculateDegree(
                ev.getX(0), ev.getY(0),
                ev.getX(1), ev.getY(1))
    }
    private fun calculateDegree(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val rad = Math.atan2(y1-y2.toDouble(), x1-x2.toDouble())
        return Math.toDegrees(rad).toFloat()
    }

}
