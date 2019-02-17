package mashup.loling.user.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import mashup.loling.R
import mashup.loling.databinding.ActivityLoginBinding
import mashup.loling.user.model.UserRepository
import mashup.loling.user.viewmodel.UserViewModel
import mashup.loling.user.viewmodel.UserViewModelFactory


class LoginActivityDataBinding : DataBindingBaseActivity<ActivityLoginBinding>() {
    private val mDisposable : CompositeDisposable = CompositeDisposable()
    private lateinit var userViewModel : UserViewModel
    override val layoutResourceId: Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProviders.of(this, UserViewModelFactory(UserRepository())).get(UserViewModel::class.java)


        viewDataBinding.setLifecycleOwner(this)
    }

    override fun onStop() {
        super.onStop()
        mDisposable.dispose()
    }
}
