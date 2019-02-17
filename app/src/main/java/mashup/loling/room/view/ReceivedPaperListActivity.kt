package mashup.loling.room.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_recive_paper_list.*
import kotlinx.android.synthetic.main.item_receive_paper_sender.*
import mashup.loling.R
import mashup.loling.room.ReceivedPage
import mashup.loling.room.RecivedPaperAdapter
import mashup.loling.user.User


class ReceivedPaperListActivity : AppCompatActivity() {
    private var reciverList = arrayListOf(
            ReceivedPage("유정", Uri.EMPTY),
            ReceivedPage("주지니",Uri.EMPTY),
            ReceivedPage("상희?",Uri.EMPTY),
            ReceivedPage("현우야",Uri.EMPTY),
            ReceivedPage("석주",Uri.EMPTY),
            ReceivedPage("해은",Uri.EMPTY),
            ReceivedPage("영은",Uri.EMPTY),
            ReceivedPage("소현",Uri.EMPTY),
            ReceivedPage("채채",Uri.EMPTY),
            ReceivedPage("시스터즈",Uri.EMPTY)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recive_paper_list)

        val mAdapter = RecivedPaperAdapter(this, reciverList)
        rvSender.adapter = mAdapter

        rvSender.layoutManager = (GridLayoutManager(this,2))
        rvSender.setHasFixedSize(true)

        layoutRecivePaperList.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            val num1 = convertPxToDp(this,layoutRecivePaperList.width).toInt()
            val num2 = convertPxToDp(this,layoutRecivePaperSender.width).toInt()
            val num3 = num1/num2
            (rvSender.layoutManager as GridLayoutManager).spanCount = num3
        }
        tvSenderListPersonName.text = User.name
        btnSenderListClose.setOnClickListener {
            finish()
        }
    }

    private fun convertPxToDp(context: Context, px: Int): Float {
        return px / context.resources.displayMetrics.density
    }
}
