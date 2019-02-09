package mashup.loling.user.model

import io.reactivex.Single
import mashup.loling.user.api.UserApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class UserRemoteData : UserDataSource {
    object UserRemoteData

    override fun register(id: String, pw: String): Single<String> {
        var retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.www.www/")
                .build()
                .create(UserApi::class.java)

        return retrofit.register(id, pw)
                .map { response ->
                    response.result
                }
    }

    override fun login(id: String, pw: String): Single<String> {
        var retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.www.www/")
                .build()
                .create(UserApi::class.java)

        return retrofit.login(id, pw)
                .map { response ->
                    response.result
                }

    }
}