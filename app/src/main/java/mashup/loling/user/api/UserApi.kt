package mashup.loling.user.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("login")
    fun login(
            @Query("id") id: String,
            @Query("pw") pw: String) : Single<Response>

    @GET("register")
    fun register(
            @Query("id") id: String,
            @Query("pw") pw: String) : Single<Response>
}