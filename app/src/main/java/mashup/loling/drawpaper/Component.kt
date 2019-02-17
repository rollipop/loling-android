package mashup.loling.drawpaper

import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import mashup.loling.drawpaper.view.IComponentTouchListener

class Component(val view: View, val componentType: ComponentType, componentSelectedListener: IComponentTouchListener) {

    init{
        view.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN) {
                componentSelectedListener.onComponentSelected(this)
            }
            return@setOnTouchListener v.onTouchEvent(event)
        }

        if(view is TextView) {
            view.setLines(1)
        }
    }

    fun rotate(deg: Float) {
        view.rotation = deg
    }

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