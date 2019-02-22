package mashup.loling.mypage

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_my_page.*
import mashup.loling.BaseActivity
import mashup.loling.R
import mashup.loling.fagment.FriendListFragment
import mashup.loling.fagment.ReceivedListFragment
import mashup.loling.fagment.WrittenListFragment
import mashup.loling.model.ERoom

class MyPageActivity : BaseActivity() {
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

    val friendFrag = FriendListFragment(dataList)
    val writtenFrag = WrittenListFragment()
    val receivedFrag = ReceivedListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        val tabs = tabsMypage
        tabs.addTab(tabs.newTab().setText(R.string.friendList))
        tabs.addTab(tabs.newTab().setText(R.string.writtenLoling))
        tabs.addTab(tabs.newTab().setText(R.string.receiveLiling))

        val pager = vpMyPage
        val adapter = PagerAdapter(supportFragmentManager, tabs.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                pager.setCurrentItem(tabs.selectedTabPosition)
            }
        })

        btnMypageSetting.setOnClickListener {
            val intent = Intent(this, MyPageSettingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_in_left)
        }

        btnMypageClose.setOnClickListener {
            finish()
        }


    }

    internal inner class PagerAdapter : FragmentStatePagerAdapter {
        var mNumOfTabs: Int

        constructor(fm: FragmentManager?, mNumOfTabs: Int) : super(fm) {
            this.mNumOfTabs = mNumOfTabs
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> friendFrag
                1 -> writtenFrag
                2 -> receivedFrag
                else -> friendFrag
            }
        }

        override fun getCount(): Int {
            return mNumOfTabs
        }
    }
}
