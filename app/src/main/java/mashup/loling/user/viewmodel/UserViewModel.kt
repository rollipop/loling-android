package mashup.loling.user.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mashup.loling.R.string.id
import mashup.loling.user.model.UserRepository

class UserViewModel(private val repository: UserRepository) : DisposableViewModel() {

    fun register(id: String, pw: String) {
        addDisposable(repository.registerFromRemote(id, pw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    TODO("응답 처리")
                }, {
                    Log.d(this.javaClass.name, "fun register() error")
                }))
    }

    fun login(id: String, pw: String) {
        Log.v("csh","login"+id+pw)
        addDisposable(repository.loginFromRemote(id, pw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    TODO("응답 처리")
                }, {
                    Log.d(this.javaClass.name, "fun login() error")
                }))
    }








}