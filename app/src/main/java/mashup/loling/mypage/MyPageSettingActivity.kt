package mashup.loling.mypage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mypage_setting.*
import mashup.loling.R

class MyPageSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_setting)

        btnMypageSettingClose.setOnClickListener {
            finish()
        }
    }
}
