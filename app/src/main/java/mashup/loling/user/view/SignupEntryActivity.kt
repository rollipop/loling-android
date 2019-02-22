package mashup.loling.user.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup_entry.*
import mashup.loling.BaseActivity
import mashup.loling.R
import mashup.loling.user.User

class SignupEntryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_entry)
         // etSignupEntryId.text
        btnSignupEntryNext.setOnClickListener { v ->
            if(etSignupEntryPw.text.toString().equals(etSignupEntryPwCheck.text.toString()) != true) {
                Toast.makeText(applicationContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(etSignupEntryId.text.toString().length < 6) {
                Toast.makeText(applicationContext, "아이디를 6글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, SignupPersonalActivity::class.java)
            User.id = etSignupEntryId.text.toString()
            User.password = etSignupEntryPw.text.toString()

            startActivity(intent)

        }

    }
}
