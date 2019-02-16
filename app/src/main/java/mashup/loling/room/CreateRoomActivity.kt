package mashup.loling.room

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_create_room.*
import kotlinx.android.synthetic.main.fragment_create_room_main.view.*
import mashup.loling.R
import mashup.loling.room.CreateRoomPagerAdapter.Companion.PAGE_CALENDAR
import mashup.loling.room.CreateRoomPagerAdapter.Companion.PAGE_EXISTED_CHK
import mashup.loling.room.CreateRoomPagerAdapter.Companion.PAGE_EXISTED_LOLING_LIST
import mashup.loling.room.CreateRoomPagerAdapter.Companion.PAGE_MAIN
import java.text.SimpleDateFormat
import java.util.*

class CreateRoomActivity : AppCompatActivity() {

    var selectedDate: Date = Date()

    val createRoomMethods = object : ICreateRoomMethods {
        override fun onSelectRoomTextClicked() {
            createRoomViewPager.currentItem = PAGE_CALENDAR
        }

        override fun onDateSelectedFromCal(date: Date) {
            selectedDate = date
            (createRoomViewPager.getTag(createRoomViewPager.hashCode() + PAGE_MAIN) as View).btnCreateLoling.isEnabled = true
            (createRoomViewPager.getTag(createRoomViewPager.hashCode() + PAGE_MAIN) as View).tvRoomDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
            createRoomViewPager.currentItem = PAGE_MAIN
        }

        override fun onCancelFromCal() {
            createRoomViewPager.currentItem = PAGE_MAIN
        }

        override fun onCreateLolingButtonClicked() {
            // Check whether loling for selected user/date is already existed or not
            TODO("not implemented check loling existed + create new loling or join existed")
            if (false) { //this should be executed as async
                // open new loling activity
                onCreateNewLolingClicked()
            } else {
                // show newLoling or joinExisted
                createRoomViewPager.currentItem = PAGE_EXISTED_CHK
            }
        }

        override fun onCreateNewLolingClicked() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onJoinExitedLolingClicked() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onExistedLolingItemClicked(lolingId: Int) {
            createRoomViewPager.currentItem = PAGE_EXISTED_LOLING_LIST
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)

        // if window size is over the 350x500 dp, set size 350x500 dp. if smaller, set match_parent
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val maxWidthPx = resources.getDimensionPixelSize(R.dimen.create_room_window_max_width)
        val maxHeightPx = resources.getDimensionPixelSize(R.dimen.create_room_window_max_height)

        window.setLayout(
                if(dm.widthPixels > maxWidthPx) maxWidthPx else WindowManager.LayoutParams.MATCH_PARENT,
                if(dm.heightPixels > maxHeightPx) maxHeightPx else WindowManager.LayoutParams.MATCH_PARENT)

        createRoomViewPager.adapter = CreateRoomPagerAdapter(this, createRoomMethods)
        createRoomViewPager.offscreenPageLimit = (createRoomViewPager.adapter as CreateRoomPagerAdapter).count
        createRoomViewPager.currentItem = PAGE_MAIN

        ivClose.setOnClickListener { finish() }

    }

    override fun onBackPressed() {
        when(createRoomViewPager.currentItem) {
            PAGE_CALENDAR -> createRoomViewPager.currentItem = PAGE_MAIN
            PAGE_EXISTED_CHK -> createRoomViewPager.currentItem = PAGE_MAIN
            PAGE_EXISTED_LOLING_LIST -> createRoomViewPager.currentItem = PAGE_EXISTED_CHK
            else -> super.onBackPressed()
        }
    }

    companion object {
        interface ICreateRoomMethods {
            fun onSelectRoomTextClicked()
            fun onDateSelectedFromCal(date: Date)
            fun onCancelFromCal()
            fun onCreateLolingButtonClicked()
            fun onCreateNewLolingClicked()
            fun onJoinExitedLolingClicked()
            fun onExistedLolingItemClicked(lolingId: Int)
        }
    }

}
