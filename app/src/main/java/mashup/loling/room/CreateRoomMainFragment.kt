package mashup.loling.room

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_create_room_main.*
import kotlinx.android.synthetic.main.fragment_create_room_main.view.*

import mashup.loling.R
import java.text.SimpleDateFormat
import java.util.*

class CreateRoomMainFragment : Fragment() {

    lateinit var createRoomMethods: CreateRoomActivity.Companion.ICreateRoomMethods
    var selectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_room_main, container, false)

        view.tvRoomDate.setOnClickListener { createRoomMethods.onSelectRoomTextClicked() }
        view.btnCreateLoling.setOnClickListener {
            selectedDate?.let { createRoomMethods.onCreateLolingButtonClicked(it) }
        }

        return view
    }

    fun setDateAndText(date: Date) {
        tvRoomDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
        selectedDate = date
        btnCreateLoling.isEnabled = true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CreateRoomMainFragment.
         */
        @JvmStatic
        fun newInstance(createRoomMethods: CreateRoomActivity.Companion.ICreateRoomMethods) =
                CreateRoomMainFragment().apply {
                    this.createRoomMethods = createRoomMethods
                }
    }
}
