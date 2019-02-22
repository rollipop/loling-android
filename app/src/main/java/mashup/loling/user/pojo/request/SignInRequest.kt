package mashup.loling.user.pojo.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SignInRequest(
        @SerializedName("userId")
        var userId : String,
        @SerializedName("password")
        var password: String)