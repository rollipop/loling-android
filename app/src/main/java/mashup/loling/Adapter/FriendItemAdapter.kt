package mashup.loling.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_friend_day_list.view.*
import mashup.loling.R
import mashup.loling.model.ERoom
import mashup.loling.room.view.CreateRoomActivity

class FriendItemAdapter(val context : Context ,var items : ArrayList<ERoom>) : RecyclerView
.Adapter<FriendItemAdapter.ViewHolder>(){

    public class ViewHolder : RecyclerView.ViewHolder{
        constructor(itemView: View) : super(itemView)

        var friendItemName : TextView = itemView.tvFriendItemName
        var friendItemBday : TextView = itemView.tvFriendItemBday
        var friendItemDday : TextView = itemView.tvFriendItemDday
        var friendItemBtn  = itemView.btnFriendItem

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
        viewHolder.friendItemName.text = item.name
        viewHolder.friendItemBday.text = item.date
        viewHolder.friendItemDday.text = "D-" + item.Dday

        viewHolder.friendItemBtn.setOnClickListener {
            val intent = Intent(context, CreateRoomActivity::class.java)
            intent.putExtra("name",item.name)
            intent.putExtra("phoneNum",item.phone)
            context.startActivity(intent)
        }

    }
}