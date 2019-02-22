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
import mashup.loling.model.FriendItem

class MyPageActivity : BaseActivity() {
    val friendFrag = FriendListFragment()
    val writtenFrag = WrittenListFragment()
    val receivedFrag = ReceivedListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        var friendListStr = friendFrag.friendsSzie().toString() + "\n" + getText(R.string.friendList)
        var writtenLolingStr = writtenFrag.friendsSzie().toString() + "\n" + getText(R.string.writtenLoling)
        var receiveLolingStr = receivedFrag.friendsSzie().toString() + "\n" + getText(R.string.receiveLoling)

        val tabs = tabsMypage
        tabs.addTab(tabs.newTab().setText(friendListStr))
        tabs.addTab(tabs.newTab().setText(writtenLolingStr))
        tabs.addTab(tabs.newTab().setText(receiveLolingStr))

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
