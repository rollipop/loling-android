package mashup.loling.user.pojo.response

import mashup.loling.user.pojo.BirthDay


data class SignInResponse(val id: Int,
                            val userId: String,
                            val name: String,
                            val phoneNumber: String,
                            val birthday: BirthDay,
                            val createdAt: String,
                            val anonymous: Boolean,
                            val token: String
)