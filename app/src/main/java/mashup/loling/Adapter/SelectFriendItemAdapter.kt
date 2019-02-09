package mashup.loling.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mashup.loling.model.FriendItem
import mashup.loling.R

class SelectFriendItemAdapter(var items : ArrayList<FriendItem>) : RecyclerView.Adapter<SelectFriendItemAdapter.ViewHolder>(){

    class ViewHolder : RecyclerView.ViewHolder{
        constructor(itemView: View) : super(itemView)

        var friendItemName : TextView
        var friendItemBday : TextView

        init {
            friendItemName = itemView.findViewById(R.id.tvSelectFriendItemName) as TextView
            friendItemBday = itemView.findViewById(R.id.tvSelectFriendItemBday) as TextView
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.selectfriend_item_friend_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.friendItemName.text = item.friendItemName
        viewHolder.friendItemBday.text = item.friendItemBday
    }
}