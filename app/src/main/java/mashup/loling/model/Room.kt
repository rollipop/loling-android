package mashup.loling.model

import java.util.*

class Room (
        var id : Int,
        var creatorId : Int,
        var parperId : Int,
        var createdAt : Date,
        var invitationLink : String,
        var invitationToken : String
)