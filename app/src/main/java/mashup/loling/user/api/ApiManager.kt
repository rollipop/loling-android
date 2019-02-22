package mashup.loling.user.api

import android.util.Log
import io.reactivex.Single
import mashup.loling.user.User
import mashup.loling.user.pojo.request.RegisterRequest
import mashup.loling.user.pojo.request.SignInRequest
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ApiManager {
    var url = "https://0ec805.emporter.eu/"

    fun register(): Single<String> {
        var retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()
                .create(UserApi::class.java)

        return Single.create{subscribe ->
            var registerRequest = RegisterRequest(User.id, User.name, User.birthday, User.phoneNumber, User.password)
            retrofit.register(registerRequest)
                    .map { response ->
                        response.name
                    }
                    .subscribe({responseResult->
                        Log.v("csh","ApiManager success : "+responseResult)
                        subscribe.onSuccess(responseResult)
                    }, {exception->
                        Log.v("csh","fail : "+exception)
                        subscribe.onError(Throwable(exception))
                    })
        }
    }

    fun login(id: String, pw: String): Single<String> {
        var retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()
                .create(UserApi::class.java)

        var signInRequest = SignInRequest(id, pw)
        return Single.create{subscribe ->
            retrofit.login(signInRequest)
                    .map { response ->
                        response.userId
                    }
                    .subscribe({responseResult->
                        Log.v("csh","success:"+responseResult)
                        subscribe.onSuccess(responseResult + "로그인 완료")
                    }, { except ->
                        Log.v("csh","fail:"+except)
                        subscribe.onError(Throwable("Login Failed"))
                    })
        }
    }
}