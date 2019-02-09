package mashup.loling.room

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_author_list.*
import mashup.loling.R
import mashup.loling.drawpaper.DrawPaperActivity

class AuthorListActivity : AppCompatActivity() {

    private var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_list)

        faBtnWrite.setImageResource(R.drawable.ic_add)
        faBtnWrite.setOnClickListener{
            val intent = Intent(context,DrawPaperActivity()::class.java)
            startActivity(intent)
        }

    }
}
