package mashup.loling

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.activity_my_page.*
import mashup.loling.Fragment.FriendListFragment
import mashup.loling.Fragment.ReceivedListFragment
import mashup.loling.Fragment.WrittenListFragment

class MyPageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        val tabs = findViewById<TabLayout>(R.id.tabsMypage)
        tabs.addTab(tabs.newTab().setText("친구목록"))
        tabs.addTab(tabs.newTab().setText("작성한 롤링"))
        tabs.addTab(tabs.newTab().setText("받은 롤링"))

        val friendFrag = FriendListFragment()
        val writtenFrag = WrittenListFragment()
        val receivedFrag = ReceivedListFragment()
        if(friendFrag != null) {
            supportFragmentManager.beginTransaction().add(R.id.container, friendFrag).commit()
        }

        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }
            override fun onTabSelected(p0: TabLayout.Tab?) {
                val position = tabs.selectedTabPosition
                var selected: Fragment ?= null
                if (position == 0) selected = friendFrag
                else if(position == 1) selected = writtenFrag
                else if(position == 2) selected = receivedFrag

                supportFragmentManager.beginTransaction().replace(R.id.container, selected!!).commit()
            }
        })


        //톱니바퀴
        btnMypageSetting.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MyPageSettingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_in_left)
        })
    }
}
