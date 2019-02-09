package mashup.loling.Fragment

import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mashup.loling.Adapter.SelectFriendAddressItemAdapter
import mashup.loling.R
import mashup.loling.model.FriendItem
import java.util.HashMap
import kotlin.collections.ArrayList
import kotlin.collections.Map

class SelectFriendAddressListFragment : Fragment {
    constructor() : super()

    val friends: ArrayList<FriendItem> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //get contract
        val dataList = ArrayList<Map<String, String>>()
        val c = context!!.contentResolver.query(ContactsContract.Contacts.CONTENT_URI
                , null, null, null
                , ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

        while (c.moveToNext()) {
            val map: HashMap<String, String> = HashMap<String, String>()
            val id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID))
            val name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
            map.put("name", name)

            val phoneCursor = context!!.contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null
                    , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null)

            if (phoneCursor!!.moveToFirst()) {
                val number = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER))
                map.put("phone", number)
            }
            phoneCursor.close()
            dataList.add(map)
        }
        c.close()


        //Log.d("머얀머얀", ""+ dataList)
        //Log.d("머얀머얀", ""+ dataList.get(0).get("name"))


        val root = inflater.inflate(R.layout.recyclerview, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)//this.context/getContext()
            makeFriendList(dataList)
            recyclerView.adapter = SelectFriendAddressItemAdapter(friends)
        }

        return root

    }

    fun makeFriendList(dataList: ArrayList<Map<String, String>>) {//연락처에서가져와 처리

        for (i in 0..dataList.size - 1) {

            friends.add(FriendItem(dataList[i]["name"].toString(), "", "", dataList[i]["phone"].toString()))
        }

    }

}