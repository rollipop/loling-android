package mashup.loling.room

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import mashup.loling.R

class CreateRoomExistedLolingListFragment : Fragment() {

    lateinit var createRoomMethods: CreateRoomActivity.Companion.ICreateRoomMethods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_room_existed_loling_list, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance(createRoomMethods: CreateRoomActivity.Companion.ICreateRoomMethods) =
                CreateRoomExistedLolingListFragment().apply {
                    this.createRoomMethods = createRoomMethods
                }
    }
}
