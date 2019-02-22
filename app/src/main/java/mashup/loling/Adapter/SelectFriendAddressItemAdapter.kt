package mashup.loling.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.selectfriend_item_address_list.view.*
import mashup.loling.model.FriendItem
import mashup.loling.R
import mashup.loling.room.view.CreateRoomActivity

class SelectFriendAddressItemAdapter(val context: Context, var items : ArrayList<FriendItem>) :
RecyclerView.Adapter<SelectFriendAddressItemAdapter.ViewHolder>(){

    class ViewHolder : RecyclerView.ViewHolder{
        constructor(itemView: View) : super(itemView)

        var addressItemName : TextView = itemView.tvSelectAddressName
        var addressItemPnum : TextView = itemView.tvSelectFriendAddressPnum
        var addressItemLayout = itemView.itemSelectAddress

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
        viewHolder.addressItemLayout.setOnClickListener {
            val intent = Intent(context,CreateRoomActivity::class.java)
            intent.putExtra("phoneNum",item.friendItemPnum)
            intent.putExtra("name",item.friendItemName)
            context.startActivity(intent)
        }

    }
}