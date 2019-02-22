package mashup.loling.user.view

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_signup_personal.*
import mashup.loling.BaseActivity
import mashup.loling.R
import java.util.*
import android.app.DatePickerDialog
import android.content.Intent
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import mashup.loling.user.User
import mashup.loling.user.api.ApiManager
import mashup.loling.user.pojo.BirthDay


class SignupPersonalActivity : BaseActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener {

    var dayOfMonth = 0
    var month = 0
    var year = 0
    val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_personal)
        etSignupPersonalBirthDay.setOnClickListener(this)

        btnSignupPersonalNext.setOnClickListener { v ->
            var birthDay = BirthDay(year, month, dayOfMonth)
            User.birthday = birthDay


            mDisposable.add(ApiManager.register()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivityDataBinding::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }, { exception ->
                        Toast.makeText(applicationContext, exception.message, Toast.LENGTH_SHORT).show()
                    }))
        }
    }

    override fun onClick(v: View?) {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val dialog = DatePickerDialog(this@SignupPersonalActivity, R.style.PickerTheme, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
        dialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        updateDisplay();
    }

    private fun updateDisplay() {

        etSignupPersonalBirthDay.setText(StringBuilder()
                .append(dayOfMonth).append("/").append(month + 1).append("/").append(year).append(" "))
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.dispose()
    }
}
