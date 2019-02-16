package mashup.loling.fagment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.item_loling_list.*
import kotlinx.android.synthetic.main.recyclerview.view.*
import mashup.loling.Adapter.FriendItemAdapter
import mashup.loling.Adapter.LolingItemAdapter
import mashup.loling.model.FriendItem
import mashup.loling.R

class WrittenListFragment : Fragment {
    constructor() : super()
    val friends: ArrayList<FriendItem> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.recyclerview, container, false)

        root.recyclerView.layoutManager = LinearLayoutManager(context)
        root.recyclerView.isNestedScrollingEnabled = false
        makeFriendList()
        root.recyclerView.adapter = LolingItemAdapter(friends)

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        btnLolingDelete.setOnClickListener (object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(v!!.context, "삭제삭제", Toast.LENGTH_LONG).show()
            }
        })*/
    }

    fun makeFriendList(){//친구목록 가져와 처리
        for (i in 0..9){
            friends.add(FriendItem("유채원" + i, "D-10" + i, "1996.03.22"))
        }
    }

}