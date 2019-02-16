package mashup.loling.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import mashup.loling.fagment.FriendListFragment
import mashup.loling.R
import mashup.loling.mypage.MyPageActivity
import mashup.loling.room.view.SelectFriendActivity


class MainActivity : AppCompatActivity() {

    private var context: Context = this
    var pageNum = 10
    private val READ_CONTACTS_PERMISSIONS_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val decorView = window.decorView
        setDecorView(decorView)

        faBtnMain.setImageResource(R.drawable.ic_add)
        contactPermissionCheck()

        val fragment = FriendListFragment()
        supportFragmentManager.beginTransaction().add(R.id.frMainFriendList, fragment).commit()

        val pager = pagerContainer.viewPager as ViewPager
        val adapter = MainPageAdepter()

        pager.adapter = adapter

        // 최소 몇 페이지 이상 볼 수 있도록 설정
        pager.offscreenPageLimit = adapter.count
        pager.pageMargin = 20
        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
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
    }

    private fun setDecorView(view : View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        } else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일
            window.statusBarColor = Color.GRAY
        }
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

    fun contactPermissionCheck(){
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
