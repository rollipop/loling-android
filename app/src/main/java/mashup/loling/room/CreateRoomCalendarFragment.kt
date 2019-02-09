package mashup.loling.room

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_create_room_calendar.view.*

import mashup.loling.R
import java.util.*

class CreateRoomCalendarFragment : Fragment() {

    lateinit var createRoomMethods: CreateRoomActivity.Companion.ICreateRoomMethods
    var selectedDate: Date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_room_calendar, container, false)

        view.btnOkay.setOnClickListener {
            selectedDate.year = view.calSelectDate.year - 1900
            selectedDate.month = view.calSelectDate.month
            selectedDate.date = view.calSelectDate.dayOfMonth
            createRoomMethods.onDateSelectedFromCal(selectedDate)
        }
        view.btnCancel.setOnClickListener { createRoomMethods.onCancelFromCal() }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CreateRoomCalendarFragment.
         */
        @JvmStatic
        fun newInstance(createRoomMethods: CreateRoomActivity.Companion.ICreateRoomMethods) =
                CreateRoomCalendarFragment().apply {
                    this.createRoomMethods = createRoomMethods
                }
    }
}
