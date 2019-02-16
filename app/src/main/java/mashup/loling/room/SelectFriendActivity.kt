package mashup.loling.room

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import mashup.loling.Fragment.SelectFriendAddressListFragment
import mashup.loling.Fragment.SelectFriendListFragment
import mashup.loling.R

class SelectFriendActivity : AppCompatActivity() {
    val friendFrag = SelectFriendListFragment()
    val addressFrag = SelectFriendAddressListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_friend)


        val tabs = findViewById<TabLayout>(R.id.tabsSelectFriend)
        tabs.addTab(tabs.newTab().setText(R.string.friend))
        tabs.addTab(tabs.newTab().setText(R.string.phone_num))

        val pager = findViewById<ViewPager>(R.id.vpSelectFriend)
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
