package mashup.loling.user.model

import io.reactivex.Single

interface UserDataSource {

    fun login(id: String, pw:String): Single<String>

    fun register(id: String, pw:String): Single<String>

}