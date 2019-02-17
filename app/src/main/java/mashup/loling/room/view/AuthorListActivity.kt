package mashup.loling.room.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_author_list.*
import mashup.loling.R
import mashup.loling.drawpaper.view.DrawPaperActivity
import mashup.loling.model.Paper
import mashup.loling.Adapter.AuthorListItemAdapter
import mashup.loling.BaseActivity
import java.util.*

class AuthorListActivity : BaseActivity() {

    private var context: Context = this
    val weterList: ArrayList<Paper> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_list)

        //지우기
        tvAuthorListAnyDay.text="2019.02.28"
        tvAuthorListAnyDayParticipant.text = "10"

        if(rvAuthorList != null) {
            rvAuthorList.layoutManager = LinearLayoutManager(context)
            writerList()
            rvAuthorList.adapter = AuthorListItemAdapter(weterList)
        }

        faBtnWrite.setImageResource(R.drawable.ic_add)
        faBtnWrite.setOnClickListener{
            val intent = Intent(context, DrawPaperActivity()::class.java)
            startActivity(intent)
        }
    }

    private fun writerList(){//친구목록 가져와 처리
        for (i in 0..19){
            weterList.add(Paper(i,i+1,i+3,"유정", Date().day, Date().hours))
        }
    }
}
