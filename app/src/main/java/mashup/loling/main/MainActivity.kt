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
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import mashup.loling.BaseActivity
import mashup.loling.R
import mashup.loling.fagment.FriendListFragment
import mashup.loling.mypage.MyPageActivity
import mashup.loling.room.view.CreateRoomActivity
import mashup.loling.room.view.SelectFriendActivity


class MainActivity : BaseActivity() {

    private var context: Context = this
    var pageNum = 10
    private val READ_CONTACTS_PERMISSIONS_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        faBtnMain.setImageResource(R.drawable.ic_add)
        contactPermissionCheck()

        val fragment = FriendListFragment()
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
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.img_main))
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
                return view2
            }
            val view = layoutInflater.inflate(R.layout.item_main_loling_room, container
                    , false)
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return pageNum
        }

    }

    private fun contactPermissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show()

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_CONTACTS)) {

            }

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    READ_CONTACTS_PERMISSIONS_REQUEST)

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
