package mashup.loling.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_select_friend.view.*
import kotlinx.android.synthetic.main.dialog_delete_list.view.*
import kotlinx.android.synthetic.main.item_loling_list.view.*
import mashup.loling.model.FriendItem
import mashup.loling.R


class LolingItemAdapter(var items : ArrayList<FriendItem>) : RecyclerView.Adapter<LolingItemAdapter.ViewHolder>(){

    var papa : ViewGroup? = null
    class ViewHolder : RecyclerView.ViewHolder{
        constructor(itemView: View) : super(itemView)

        var friendItemName : TextView = itemView.tvLolingItem
        var friendItemBday : TextView = itemView.tvLolingItemBday
        var friendItemDday : TextView = itemView.tvLolingItemDday
        var btnDelete: ImageButton = itemView.btnLolingDelete

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loling_list, parent, false)
        papa=parent
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

        viewHolder.btnDelete.setOnClickListener { v ->
            var builder = AlertDialog.Builder(v!!.context)
            var mView = LayoutInflater.from(v.context)
                    .inflate(R.layout.dialog_delete_list, papa, false)
            builder.setView(mView)
            var dialog = builder.create()
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

        }

    }

}