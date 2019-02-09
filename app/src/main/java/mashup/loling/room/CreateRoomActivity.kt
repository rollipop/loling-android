package mashup.loling.room

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_create_room.*
import mashup.loling.R
import java.util.*

class CreateRoomActivity : AppCompatActivity() {

    private val CALENDAR_FRAGMENT = 0
    private val MAIN_FRAGMENT = 1
    private val EXISTED_CHK_FRAGMENT = 2
    private val EXISTED_LOLING_LIST_FRAGMENT = 3

    val createRoomMethods = object : ICreateRoomMethods {
        override fun onSelectRoomTextClicked() {
            createRoomViewPager.currentItem = CALENDAR_FRAGMENT
        }

        override fun onDateSelectedFromCal(date: Date) {
            val adapter = createRoomViewPager.adapter as CreateRoomPagerAdapter
            (adapter.getItem(MAIN_FRAGMENT) as CreateRoomMainFragment).setDateAndText(date)
            createRoomViewPager.currentItem = MAIN_FRAGMENT
        }

        override fun onCancelFromCal() {
            createRoomViewPager.currentItem = MAIN_FRAGMENT
        }

        override fun onCreateLolingButtonClicked(date: Date) {
            // Check whether loling for selected user/date is already existed or not
            TODO("not implemented check loling existed + create new loling or join existed")
            if(false) { //this should be executed as async
                // open new loling activity
                onCreateNewLolingClicked()
            } else {
                // show newLoling or joinExisted
                createRoomViewPager.currentItem = EXISTED_CHK_FRAGMENT
            }
        }

        override fun onCreateNewLolingClicked() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onJoinExitedLolingClicked() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onExistedLolingItemClicked(lolingId: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room)
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        createRoomViewPager.adapter = CreateRoomPagerAdapter(supportFragmentManager, createRoomMethods)
        createRoomViewPager.currentItem = 1

        ivClose.setOnClickListener { finish() }

    }

    companion object {
        interface ICreateRoomMethods {
            fun onSelectRoomTextClicked()
            fun onDateSelectedFromCal(date: Date)
            fun onCancelFromCal()
            fun onCreateLolingButtonClicked(date: Date)
            fun onCreateNewLolingClicked()
            fun onJoinExitedLolingClicked()
            fun onExistedLolingItemClicked(lolingId: Int)
        }
    }

}
