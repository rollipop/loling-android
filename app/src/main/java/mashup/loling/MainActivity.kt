package mashup.loling

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import mashup.loling.model.FriendItem

class MainActivity : AppCompatActivity() {

    var friendItemList = arrayListOf<FriendItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
