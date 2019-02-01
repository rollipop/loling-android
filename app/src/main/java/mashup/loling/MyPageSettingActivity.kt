package mashup.loling

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_mypage_setting.*

class MyPageSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_setting)

        btnMypageSettingClose.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
}
