package mashup.loling.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mashup.loling.Adapter.SelectFriendItemAdapter
import mashup.loling.model.FriendItem
import mashup.loling.R

class SelectFriendListFragment : Fragment {
    constructor() : super()
    val friends: ArrayList<FriendItem> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.recyclerview, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        if(recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)//this.context/getContext()
            makeFriendList()
            recyclerView.adapter = SelectFriendItemAdapter(friends)
        }

        return root

    }

    fun makeFriendList(){//친구목록 가져와 처리
        for (i in 0..9){
            friends.add(FriendItem("유채원" + i, "1996.03.22"))
        }
    }

}