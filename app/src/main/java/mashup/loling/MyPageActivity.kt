package mashup.loling

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_my_page.*
import mashup.loling.Fragment.FriendListFragment
import mashup.loling.Fragment.ReceivedListFragment
import mashup.loling.Fragment.WrittenListFragment

class MyPageActivity : AppCompatActivity() {
    val friendFrag = FriendListFragment()
    val writtenFrag = WrittenListFragment()
    val receivedFrag = ReceivedListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        val tabs = findViewById<TabLayout>(R.id.tabsMypage)
        tabs.addTab(tabs.newTab().setText("친구목록"))
        tabs.addTab(tabs.newTab().setText("작성한 롤링"))
        tabs.addTab(tabs.newTab().setText("받은 롤링"))

        var pager = findViewById<ViewPager>(R.id.vpMyPage)
        val adapter = PagerAdapter(supportFragmentManager,tabs.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }
            override fun onTabSelected(p0: TabLayout.Tab?) {
                pager.setCurrentItem(tabs.selectedTabPosition)
            }
        })
        //톱니바퀴
        btnMypageSetting.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MyPageSettingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_in_left)
        })
    }

    internal inner class PagerAdapter : FragmentStatePagerAdapter {
        var mNumOfTabs: Int
        constructor(fm: FragmentManager?, mNumOfTabs: Int) : super(fm) {
            this.mNumOfTabs = mNumOfTabs
        }
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    return friendFrag
                }
                1 -> {
                    return writtenFrag
                }
                2 -> {
                    return receivedFrag
                }
                else -> return friendFrag
            }
        }
        override fun getCount(): Int {
            return mNumOfTabs
        }
    }
}
