package mashup.loling.user

import java.util.*

/**
 * 현재 로그인한 유저의 정보를 담고있는 싱글톤
 */
object User {
    var id: Int = -1
    var password: String = ""
    var salt: String = ""
    var encryptedPhoneNumber: String = ""
    var name: String = ""
    var phoneNumber: String = ""
    var birthday: Date = Date()
    var createdAt: Date = Date()
    var anonymous: Boolean = false
    var token: String = ""
}
