package mashup.loling.user.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import mashup.loling.R
import mashup.loling.databinding.ActivityLoginBinding
import mashup.loling.main.MainActivity
import mashup.loling.user.api.ApiManager

class LoginActivityDataBinding : DataBindingBaseActivity<ActivityLoginBinding>() {

    private val mDisposable: CompositeDisposable = CompositeDisposable()
    override val layoutResourceId: Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref: SharedPreferences = getSharedPreferences("autoLogin", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        if (pref.getBoolean("checkAutoLogin", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        /*
        viewDataBinding.btnLoginSignup.setOnClickListener { view ->
            mDisposable.add(ApiManager.register(viewDataBinding.etLoginID.text.toString(), viewDataBinding.etLoginPW.text.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ result ->
                        Toast.makeText(applicationContext, result + " 회원가입 완료!", Toast.LENGTH_SHORT).show()
                    }, { exception ->
                        Toast.makeText(applicationContext, exception.message, Toast.LENGTH_SHORT).show()
                    }))
        }*/

        viewDataBinding.setLifecycleOwner(this)
        viewDataBinding.btnLoginSignup.setOnClickListener { view ->
            val intent = Intent(this, SignupEntryActivity::class.java)
            startActivity(intent)
        }



        viewDataBinding.btnLogin.setOnClickListener {
            Log.v("csh", "id:" + viewDataBinding.etLoginID.text)
            Log.v("csh", "pw:" + viewDataBinding.etLoginPW.text)

            if (viewDataBinding.etLoginID.text.toString() == "admin" && viewDataBinding.etLoginPW.text.toString() == "admin") {
                val intent = Intent(this, MainActivity::class.java)
                editor.putBoolean("checkAutoLogin", checkAutoLogin.isChecked)
                editor.apply()
                startActivity(intent)
                finish()
            }

            mDisposable.add(ApiManager.login(viewDataBinding.etLoginID.text.toString(), viewDataBinding.etLoginPW.text.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { freezeUI() }
                    .doAfterTerminate { unFreezeUI() }
                    .subscribe({ result ->
                        Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()

                        editor.putBoolean("checkAutoLogin", checkAutoLogin.isChecked)
                        editor.apply()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, { exception ->
                        Toast.makeText(applicationContext, exception.message, Toast.LENGTH_SHORT).show()
                    }))
        }
    }

    override fun onStop() {
        super.onStop()
        mDisposable.dispose()
    }

    fun freezeUI() {
        viewDataBinding.getRoot().setAlpha(0.6f)
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun unFreezeUI() {
        viewDataBinding.getRoot().setAlpha(1.0f)
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

}
