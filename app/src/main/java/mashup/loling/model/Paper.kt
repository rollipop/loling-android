package mashup.loling.model

class Paper(
        var id: Int,
        var creatorId: Int,
        var receiverId: Int,
        var name: String,
        var createdAt: Int,
        var dueDay: Int
)