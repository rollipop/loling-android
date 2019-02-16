package mashup.loling.Adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_create_room_calendar.view.*
import kotlinx.android.synthetic.main.layout_create_room_main.view.*
import kotlinx.android.synthetic.main.layout_create_room_new_or_enter.view.*
import mashup.loling.R
import mashup.loling.room.view.CreateRoomActivity
import java.util.*

class CreateRoomPagerAdapter(val context: Context,
                             val createRoomMethods : CreateRoomActivity.Companion.ICreateRoomMethods)
    : PagerAdapter() {

    companion object {
        val PAGE_CALENDAR = 0
        val PAGE_MAIN = 1
        val PAGE_EXISTED_CHK = 2
        val PAGE_EXISTED_LOLING_LIST = 3
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        lateinit var view: ViewGroup
        val inflater = LayoutInflater.from(context)
        when(position) {
            PAGE_CALENDAR -> {
                view = inflater.inflate(R.layout.layout_create_room_calendar, container, false) as ViewGroup
                view.btnOkay.setOnClickListener {
                    val selDate = Date()
                    selDate.year = view.calSelectDate.year - 1900
                    selDate.month = view.calSelectDate.month
                    selDate.date = view.calSelectDate.dayOfMonth
                    createRoomMethods.onDateSelectedFromCal(selDate)
                }
                view.btnCancel.setOnClickListener { createRoomMethods.onCancelFromCal() }
            }

            PAGE_EXISTED_CHK -> {
                view = inflater.inflate(R.layout.layout_create_room_new_or_enter, container, false) as ViewGroup
                view.btnCreateNewLoling.setOnClickListener { createRoomMethods.onCreateNewLolingClicked() }
                view.btnJoinExistedLoling.setOnClickListener { createRoomMethods.onJoinExitedLolingClicked() }
            }

            PAGE_EXISTED_LOLING_LIST -> {
                view = inflater.inflate(R.layout.layout_create_room_existed_loling_list, container, false) as ViewGroup

            }
            else-> { //PAGE_MAIN
                view = inflater.inflate(R.layout.layout_create_room_main, container, false) as ViewGroup
                view.tvRoomDate.setOnClickListener { createRoomMethods.onSelectRoomTextClicked() }
                view.btnCreateLoling.setOnClickListener { createRoomMethods.onCreateLolingButtonClicked() }
            }
        }

        container.addView(view)
        container.setTag(container.hashCode() + position, view)
        return view
    }



    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return 4    //only 4 items
    }

}