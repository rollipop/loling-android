package mashup.loling.drawpaper

import android.view.MotionEvent
import android.view.View
import mashup.loling.drawpaper.view.IComponentTouchListener

class Component(val view: View, val componentType: ComponentType, componentSelectedListener: IComponentTouchListener) {

    init{
        view.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN) {
                componentSelectedListener.onComponentSelected(this)
            }
            return@setOnTouchListener v.onTouchEvent(event)
        }

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

    companion object {
        enum class ComponentType {
            TEXT, IMAGE
        }
    }
}