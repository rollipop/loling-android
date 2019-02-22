package mashup.loling.drawpaper.view

import mashup.loling.drawpaper.Component

interface IComponentTouchListener {
    fun onComponentTouchedDown(component: Component)
    fun onComponentClicked(component: Component)
    fun onComponentLongClicked(component: Component)
}