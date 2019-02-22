package mashup.loling.fagment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recyclerview.view.*
import mashup.loling.Adapter.FriendItemAdapter
import mashup.loling.model.FriendItem
import mashup.loling.R
import mashup.loling.model.ERoom

@SuppressLint("ValidFragment")
class FriendListFragment(dataList: ArrayList<ERoom>) : Fragment() {
    val friends: ArrayList<ERoom> = dataList

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.recyclerview, container, false)

        root.recyclerView.layoutManager = LinearLayoutManager(context)
//        root.recyclerView.isNestedScrollingEnabled = false
//        makeFriendList()
        root.recyclerView.adapter = FriendItemAdapter(
                context!!,friends)

        return root

    }


//    fun makeFriendList(){//친구목록 가져와 처리
//        for (i in 0..15){
//            friends.add(FriendItem("유채원" + i, "D-10" + i, "1996.03.22"))
//        }
//    }

    fun friendsSzie(): Int{
        //makeFriendList()
        return friends.size
    }

}