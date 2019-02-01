package mashup.loling.model

import android.widget.TextView

class FriendItem (var friendItemName: String, var friendItemDday: String, var friendItemBday: String){
    override fun toString(): String {
        return "FriendItem(friendItemName='$friendItemName', friendItemDday='$friendItemDday', friendItemBday='$friendItemBday')"
    }
}
