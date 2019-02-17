package mashup.loling.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_paper_noti.*
import mashup.loling.R
import mashup.loling.room.view.ReceivedPaperListActivity

class PaperNotiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paper_noti)

        btnPagerNotification.setOnClickListener {
            val intent = Intent(this,ReceivedPaperListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
