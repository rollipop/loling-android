package mashup.loling.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import mashup.loling.model.FriendItem
import mashup.loling.R

class SelectFriendAddressItemAdapter(var items : ArrayList<FriendItem>) : RecyclerView.Adapter<SelectFriendAddressItemAdapter.ViewHolder>(){

    class ViewHolder : RecyclerView.ViewHolder{
        constructor(itemView: View) : super(itemView)

        var addressItemName : TextView
        var addressItemPnum : TextView

        init {
            addressItemName = itemView.findViewById(R.id.tvSelectAddressName) as TextView
            addressItemPnum = itemView.findViewById(R.id.tvSelectFriendAddressPnum) as TextView
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.selectfriend_item_address_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.addressItemName.text = item.friendItemName
        viewHolder.addressItemPnum.text = item.friendItemPnum
    }
}