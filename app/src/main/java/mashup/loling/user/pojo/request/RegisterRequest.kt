package mashup.loling.user.pojo.request

import com.google.gson.annotations.SerializedName
import mashup.loling.user.pojo.BirthDay
import java.io.Serializable

data class RegisterRequest(
        @SerializedName("id")
        var id : String,
        @SerializedName("name")
        var name: String,
        @SerializedName("birthday")
        var birthday: BirthDay,
        @SerializedName("phoneNumber")
        var phoneNumber: String,
        @SerializedName("password")
        var password: String)