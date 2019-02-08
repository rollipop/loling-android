package mashup.loling.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_loling_list.view.*
import mashup.loling.model.FriendItem
import mashup.loling.R


class LolingItemAdapter(var items : ArrayList<FriendItem>) : RecyclerView.Adapter<LolingItemAdapter.ViewHolder>(){

    public class ViewHolder : RecyclerView.ViewHolder{
        constructor(itemView: View) : super(itemView)

        var friendItemName : TextView = itemView.tvLolingItem
        var friendItemBday : TextView = itemView.tvLolingItemBday
        var friendItemDday : TextView = itemView.tvLolingItemDday

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loling_list, parent, false)
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