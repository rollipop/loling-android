package mashup.loling.room

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_receive_paper_sender.view.*
import mashup.loling.R
import mashup.loling.room.view.ReceivedPaperRoomActivity

class RecivedPaperAdapter(val context: Context, val receiverList: List<ReceivedPage>):
        RecyclerView.Adapter<RecivedPaperAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viweType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(
                R.layout.item_receive_paper_sender, parent, false
        )
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return receiverList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(receiverList[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sender = itemView.tvSender

        fun bind(receivePaper: ReceivedPage) {
            sender.text = receivePaper.sender

            sender.setOnClickListener{
                val intent = Intent(context,ReceivedPaperRoomActivity()::class.java)
                intent.putExtra("position",position)
                val nameList = ArrayList<String>()
                val uriList = ArrayList<String>()

                for(receivedRoom in receiverList) {
                    nameList.add(receivedRoom.sender)
                    uriList.add(receivedRoom.image.toString())
                }

                intent.putStringArrayListExtra("nameList", nameList)
                intent.putStringArrayListExtra("uriList", uriList)
                context.startActivity(intent)
            }
        }
    }


}