package mashup.loling.mypage

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_mypage_setting.*
import mashup.loling.BaseActivity
import mashup.loling.R

class MyPageSettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_setting)

        btnMypageSettingClose.setOnClickListener {
            finish()
        }
    }
}
