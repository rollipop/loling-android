package mashup.loling.drawpaper

import android.graphics.PointF
import android.view.MotionEvent
import android.view.View

class Component(val view: View) {
    var position: PointF = PointF(0f,0f)
    var rotation: Float = 0f
    var scale: PointF = PointF(1f, 1f)

    var middlePoint = PointF(0f, 0f)
    var originTouchPoint = PointF(0f, 0f)
    var originDistance = 0f
    var originDegree = 0f

    init{
        view.setOnTouchListener { v, event ->
            onTouchInput(event)
            return@setOnTouchListener v.onTouchEvent(event)
        }
    }

    private fun onTouchInput(ev: MotionEvent) {
        val lParam = view.layoutParams ?: return
        var pointerCnt = ev.pointerCount
        pointerCnt = if(pointerCnt > 2) 2 else pointerCnt   // 최대 멀티터치는 두개까지만 제한
        originTouchPoint = calculateMiddlePoint(ev)

        when(ev.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_POINTER_DOWN -> {    // 터치 다운
                originTouchPoint = middlePoint
                if(pointerCnt == 2) {
                    originDistance = calculateDistance(ev)
                    originDegree = calculateDegree(ev)
                }
            }

            MotionEvent.ACTION_MOVE -> {    // 무-브
                val dMove = calculateMiddlePoint(ev)
                dMove.offset(-originTouchPoint.x, -originTouchPoint.y)
                val dDist = calculateDistance(ev) - originDistance
                val dDeg = calculateDegree(ev) - originDegree

                // Todo : View's position, rotation, scale
            }

            MotionEvent.ACTION_POINTER_UP -> {  // 터치 업
                originTouchPoint = middlePoint
            }
        }
    }

    private fun calculateMiddlePoint(ev: MotionEvent): PointF {
        val pointerCnt = ev.pointerCount
        val newPoint = PointF(0f, 0f)
        for(i in 0 until pointerCnt) {
            newPoint.offset(ev.getX(i), ev.getY(i))
        }

        if (pointerCnt > 0) {
            newPoint.x /= pointerCnt
            newPoint.y /= pointerCnt
        }

        return newPoint
    }

    private fun calculateDistance(ev: MotionEvent): Float {
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
        return calculateDegree(
                ev.getX(0), ev.getY(0),
                ev.getX(1), ev.getY(1))
    }
    private fun calculateDegree(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val slope = (y1-y2)/(x1-x2)
        return Math.atan(slope.toDouble()).toFloat()
    }
}