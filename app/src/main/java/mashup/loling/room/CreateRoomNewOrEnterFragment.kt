package mashup.loling.room

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_create_room_new_or_enter.view.*

import mashup.loling.R

class CreateRoomNewOrEnterFragment : Fragment() {

    lateinit var createRoomMethods: CreateRoomActivity.Companion.ICreateRoomMethods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_room_new_or_enter, container, false)

        view.btnCreateNewLoling.setOnClickListener { createRoomMethods.onCreateNewLolingClicked() }
        view.btnJoinExistedLoling.setOnClickListener { createRoomMethods.onJoinExitedLolingClicked() }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CreateRoomNewOrEnterFragment.
         */
        @JvmStatic
        fun newInstance(createRoomMethods: CreateRoomActivity.Companion.ICreateRoomMethods) =
                CreateRoomNewOrEnterFragment().apply {
                    this.createRoomMethods = createRoomMethods
                }
    }
}
