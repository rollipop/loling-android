package mashup.loling.user.pojo.response

import mashup.loling.user.pojo.BirthDay
import mashup.loling.user.pojo.ProfileImage

data class RegisterResponse(val id: Int,
                            val userId: String,
                            val name: String,
                            val birthday: BirthDay,
                            val phoneNumber: String,
                            val createdAt: String,
                            val anonymous: Boolean
                    )