package mashup.loling.room.view

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_select_friend.*
import mashup.loling.BaseActivity
import mashup.loling.R
import mashup.loling.fagment.SelectFriendAddressListFragment
import mashup.loling.fagment.SelectFriendListFragment

class SelectFriendActivity : BaseActivity() {
    val friendFrag = SelectFriendListFragment()
    val addressFrag = SelectFriendAddressListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_friend)

        val tabs = tabsSelectFriend
        tabs.addTab(tabs.newTab().setText(R.string.friend))
        tabs.addTab(tabs.newTab().setText(R.string.phone_num))

        val pager = vpSelectFriend
        val adapter = PagerAdapter(supportFragmentManager,tabs.tabCount)
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }
            override fun onTabSelected(p0: TabLayout.Tab?) {
                pager.currentItem = tabs.selectedTabPosition
            }
        })



    }

    internal inner class PagerAdapter(fm: FragmentManager?, private var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    friendFrag
                }
                1 -> {
                    addressFrag
                }
                else -> friendFrag
            }
        }
        override fun getCount(): Int {
            return mNumOfTabs
        }
    }
}
