package mashup.loling.user.model

import io.reactivex.Single

class UserRepository {
    object UserRepository

    private val userRemoteData = UserRemoteData()

    fun registerFromRemote(id: String, pw: String) : Single<String> {
        return userRemoteData.register(id, pw)
    }

    fun loginFromRemote(id: String, pw: String) : Single<String> {
        return userRemoteData.login(id, pw)
    }




}