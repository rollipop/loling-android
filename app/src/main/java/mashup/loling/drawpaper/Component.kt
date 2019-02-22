package mashup.loling.drawpaper

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.TextView
import mashup.loling.R
import mashup.loling.drawpaper.view.IComponentTouchListener

class Component(val view: View,
                var zIndex: Int,
                val componentTouchedListener: IComponentTouchListener) {

    var textSize = 20f

    init{
        view.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN) {
                componentTouchedListener.onComponentTouchedDown(this)
            }

            return@setOnTouchListener v.onTouchEvent(event)
        }

        // set outlineProvider to BOUNDS for elevation
        view.outlineProvider = ViewOutlineProvider.BOUNDS

    }

    /** 현재 꾸미기 컴포넌트의 뷰를 회전한다
     * @param deg (절대값)회전할 각도(degree). 이 각도로 회전 */
    fun rotate(deg: Float) {
        view.rotation = deg
    }

    /** 현재 꾸미기 컴포넌트의 뷰를 확대/축소한다
     * @param scale (절대값)확대/축소시킬 scale값. 1.0이 기본 크기 */
    fun scale(scale: Float) {
        view.scaleX = scale
        view.scaleY = scale
    }

    /**
     * 이 꾸미기 컴포넌트가 선택됐을 때 불리는 메소드
     */
    fun onComponentSelected() {

    }

    /**
     * 이 꾸미기 컴포넌트가 선택해제됐을 때 불리는 메소드
     */
    fun onComponentUnselected() {

    }

    fun increaseTextSizeRequest() {
        if(textSize >= 150) return
        if(view !is TextView) return
        textSize += 5
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)

    }

    fun decreaseTextSizeRequest() {
        if(textSize <= 20) return
        if(view !is TextView) return
        textSize -= 5
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
    }

    fun onTextColorChangeRequest(colorInt: Int) {
        if(view is TextView) {
            view.setTextColor(colorInt)
        }
    }

    fun onTextBackgroundChangeRequest(colorInt: Int) {
        view.setBackgroundColor(colorInt)
    }
}