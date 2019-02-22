package mashup.loling

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import mashup.loling.main.MainActivity
import mashup.loling.user.view.LoginActivityDataBinding

class IntroActivity : BaseActivity(){
    var r: Runnable = Runnable {
        val intent = Intent(applicationContext, LoginActivityDataBinding::class.java)
        startActivity(intent)

        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        var handler: Handler = Handler()
        handler.postDelayed(r, 3000)
    }
}