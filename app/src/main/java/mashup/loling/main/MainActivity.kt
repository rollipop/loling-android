package mashup.loling.main

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_main_loling_room.view.*
import mashup.loling.BaseActivity
import mashup.loling.R
import mashup.loling.fagment.FriendListFragment
import mashup.loling.model.ERoom
import mashup.loling.mypage.MyPageActivity
import mashup.loling.room.view.CreateRoomActivity
import mashup.loling.room.view.SelectFriendActivity


class MainActivity : BaseActivity() {

    private var dataList = arrayListOf(
            ERoom("생일축하", 1, "소현", "2019.02.23", 30, "01025017444"),
            ERoom("100일 기념", 1, "유정", "2019.02.23", 10, "01025017444"),
            ERoom("생일축하", 4, "주진", "2019.02.26", 3, "01025017444"),
            ERoom("생일축하", 4, "상희", "2019.02.26", 22, "01025017444"),
            ERoom("생일축하", 5, "민수", "2019.02.28", 22, "01025017444"),
            ERoom("생일축하", 8, "김희철", "2019.03.3", 22, "01025017444"),
            ERoom("졸업", 11, "강민석", "2019.03.5", 1, "01025017444"),
            ERoom("생일축하", 12, "김유정", "2019.03.6", 4, "01025017444"),
            ERoom("생일축하", 18, "이민희", "2019.03.11", 110, "01025017444"),
            ERoom("생일축하", 30, "elden", "2019.03.23", 2, "01025017444"),
            ERoom("생일축하", 50, "happy", "2019.04.10", 4, "01025017444"),
            ERoom("생일축하", 51, "이민상", "2019.04.11", 22, "01025017444"),
            ERoom("생일축하", 52, "최상희", "2019.04.12", 4, "01025017444"),
            ERoom("생일축하", 60, "소연", "2019.04.20", 2, "01025017444")
    )

    private var context: Context = this

    private val READ_CONTACTS_PERMISSIONS_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pageNum = dataList.size
        if (pageNum>9) {
            pageNum = 9
        }
        faBtnMain.setImageResource(R.drawable.ic_add)
        contactPermissionCheck()

        val fragment = FriendListFragment(dataList)
        supportFragmentManager.beginTransaction().add(R.id.frMainFriendList, fragment).commit()

        val pager = pagerContainer.viewPager as ViewPager
        val adapter = MainPageAdepter()

        pager.adapter = adapter

        pager.offscreenPageLimit = adapter.count
        pager.pageMargin = 20
        pager.clipChildren = false

        pagerIndicator?.createDotPanel(pageNum, R.drawable.indicator_dot_off, R.drawable.indicator_dot_on, pager.currentItem)
        pagerContainer.setIndicator(pagerIndicator)

        faBtnMain.setOnClickListener {
            val intent = Intent(context, SelectFriendActivity()::class.java)
            startActivity(intent)
        }
        btnMainSettingFriend.setOnClickListener {
            val intent = Intent(context, MyPageActivity()::class.java)
            startActivity(intent)
        }

        //채널생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("22", name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
            /*  채널 삭제
            val id: String = "my_channel_01"
            notificationManager.deleteNotificationChannel(id)*/
        }
        val resultIntent = Intent(this, PaperNotiActivity::class.java)
        startActivity(resultIntent)
        val mPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent
                .FLAG_UPDATE_CURRENT)

        val mBuilder = NotificationCompat.Builder(this, "22")
                .setSmallIcon(R.drawable.indicator_dot_on)
                .setContentTitle("축하합니다! 롤링페이퍼가 도착했습니다.")
                .setContentText("롤링페이퍼 보러가기!")
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.img_main))
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(mPendingIntent)
        val mNotificationManager: NotificationManager = (getSystemService(NOTIFICATION_SERVICE)) as NotificationManager
        mNotificationManager.notify(0, mBuilder.build())
    }

    private inner class MainPageAdepter : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            if (position == 9) {
                val view2 = layoutInflater.inflate(R.layout.item_main_loling_room_max
                        , container, false)
                container.addView(view2)
                view2.setOnClickListener {
                    intent = Intent(context, SelectFriendActivity::class.java)
                    startActivity(intent)
                }
                return view2
            }
            val view = layoutInflater.inflate(R.layout.item_main_loling_room, container
                    , false)
            container.addView(view)
            view.tvLolingRoomTitle.text = dataList[position].title
            view.tvLolingRoomDday.text = "D - " + dataList[position].Dday
            view.tvLolingRoomTarget.text = dataList[position].name + "님에게"
            view.tvLolingRoomDate.text = dataList[position].date
            view.tvLolingRoomCountParticipant.text = dataList[position].participant.toString()
            view.setOnClickListener {
                intent = Intent(context, CreateRoomActivity::class.java)
                intent.putExtra("name", dataList[position].name)
                intent.putExtra("phoneNum", dataList[position].phone)
                startActivity(intent)
            }
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            if (dataList.size > 9) {
                return 10
            }
            return dataList.size
        }

    }

    private fun contactPermissionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_CONTACTS)) {

            }
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    3)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            READ_CONTACTS_PERMISSIONS_REQUEST -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return
            }
        }
    }
}
