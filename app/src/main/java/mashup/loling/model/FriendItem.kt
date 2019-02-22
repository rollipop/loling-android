package mashup.loling.model

class FriendItem(
        var friendItemName: String,
        var friendItemDday: String,
        var friendItemBday: String,
        var friendItemPnum: String) {
    constructor(friendItemName: String, friendItemDday: String, friendItemBday: String) : this(friendItemName, friendItemDday, friendItemBday, "")
    constructor(friendItemName: String, friendItemBday: String) : this(friendItemName, "", friendItemBday, "")
    //constructor(name: String, friendItemPnum: String) : this(name, "", "", friendItemPnum)


    override fun toString(): String {
        return "FriendItem(name='$friendItemName', friendItemDday='$friendItemDday', friendItemBday='$friendItemBday')"
    }
}
