package mashup.loling.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mashup.loling.model.FriendItem
import mashup.loling.R

class FriendItemAdapter(var items : ArrayList<FriendItem>) : RecyclerView.Adapter<FriendItemAdapter.ViewHolder>(){

    public class ViewHolder : RecyclerView.ViewHolder{
        constructor(itemView: View) : super(itemView)

        var friendItemName : TextView
        var friendItemBday : TextView
        var friendItemDday : TextView

        init {
            friendItemName = itemView.findViewById(R.id.tvFriendItemName) as TextView
            friendItemBday = itemView.findViewById(R.id.tvFriendItemBday) as TextView
            friendItemDday = itemView.findViewById(R.id.tvFriendItemDday) as TextView
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_friend_day_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.friendItemName.text = item.friendItemName
        viewHolder.friendItemBday.text = item.friendItemBday
        viewHolder.friendItemDday.text = item.friendItemDday
    }
}