package mashup.loling.room

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class CreateRoomPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private val item0CalendarFragment = CreateRoomCalendarFragment.newInstance()
    private val item1MainFragment = CreateRoomMainFragment.newInstance()
    private val item2NewOrEnterFragment = CreateRoomNewOrEnterFragment.newInstance()

    override fun getItem(p0: Int): Fragment {
        return when(p0) {
            0 -> item0CalendarFragment
            1 -> item1MainFragment
            2 -> item2NewOrEnterFragment
            else -> item1MainFragment   //default
        }
    }

    override fun getCount(): Int {
        return 3    //only 3 item
    }

}