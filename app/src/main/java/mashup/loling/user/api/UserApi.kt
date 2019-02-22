package mashup.loling.user.api

import io.reactivex.Single
import mashup.loling.user.pojo.request.RegisterRequest
import mashup.loling.user.pojo.response.RegisterResponse
import mashup.loling.user.pojo.request.SignInRequest
import mashup.loling.user.pojo.response.SignInResponse
import retrofit2.http.*

interface UserApi {

    /**
     * 로그인
     */
    @Headers("Content-Type: application/json")
    @POST("auth/sign-in")
    fun login(@Body body: SignInRequest) : Single<SignInResponse>


    /**
     * 회원가입
     */
    @Headers("Content-Type: application/json")
    @POST("users")
    fun register(@Body body: RegisterRequest) : Single<RegisterResponse>
}