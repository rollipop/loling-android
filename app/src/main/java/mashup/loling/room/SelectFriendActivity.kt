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
        tabs.addTab(tabs.newTab().setText("친구"))
        tabs.addTab(tabs.newTab().setText("연락처"))

        var pager = findViewById<ViewPager>(R.id.vpSelectFriend)
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
                    return addressFrag
                }
                else -> return friendFrag
            }
        }
        override fun getCount(): Int {
            return mNumOfTabs
        }
    }
}
