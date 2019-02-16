package mashup.loling.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_author_list.view.*
import mashup.loling.R
import mashup.loling.model.Paper


class AuthorListItemAdapter(var items : ArrayList<Paper>) : RecyclerView
.Adapter<AuthorListItemAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var authorListItemName : TextView = itemView.tvAuthorListItemName
        var authorListItemTime : TextView = itemView.tvAuthorListItemDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_author_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        viewHolder.authorListItemName.text = item.name
        viewHolder.authorListItemTime.text = item.createdAt.toString()+ item.dueDay.toString()
    }
}