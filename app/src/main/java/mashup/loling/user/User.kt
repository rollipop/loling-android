package mashup.loling.user

import java.util.*

/**
 * 현재 로그인한 유저의 정보를 담고있는 싱글톤
 */
object User {
    var id: Int = 123456
    var salt: String = ""
    var encryptedPhoneNumber: String = ""
    var name: String = "유댕"
    var phoneNumber: String = "01025017444"
    var birthday: Date = Date()
    var createdAt: Date = Date()
    var anonymous: Boolean = false
    var token: String = ""
}
