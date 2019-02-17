package mashup.loling.room.view

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_author_list_detail.*
import kotlinx.android.synthetic.main.item_author_list_detail.view.*
import mashup.loling.R
import mashup.loling.room.ReceivedPage

class ReceivedPaperRoomActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_list_detail)

        val nameList = intent.getStringArrayListExtra("nameList")
        val uriList = intent.getStringArrayListExtra("uriList")
        val receivedList = ArrayList<ReceivedPage>()

        for(i in 0 until nameList.size) {
            receivedList.add(ReceivedPage(nameList[i], Uri.parse(uriList[i])))
        }

        val pager = authorListPageContainer.viewPager as ViewPager
        val adapter = AuthorListAdepter(receivedList)

        pager.adapter = adapter

        // 최소 몇 페이지 이상 볼 수 있도록 설정
        pager.offscreenPageLimit = adapter.count
        pager.pageMargin = 8
        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.clipChildren = false
        pager.currentItem = intent.getIntExtra("position",0)

        tvAuthorListDetailPageMax.text = "/${receivedList.size}"
        btnAuthorListDetailClose.setOnClickListener {
            finish()
        }
    }


    private inner class AuthorListAdepter(var receivedList: ArrayList<ReceivedPage>) : PagerAdapter
    () {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = layoutInflater.inflate(R.layout.item_author_list_detail, container
                    , false)
            container.addView(view)
            view.tvSenderName.text=receivedList[position].sender
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return receivedList.size
        }

    }
}
