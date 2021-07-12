package ir.ha.daneshyar.ui.fragments.dialogFragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ir.formol.R
import ir.ha.daneshyar.models.EventModel
import ir.ha.daneshyar.utils.Utilities
import java.util.*

class AddEventDialogFragment(private val confirmClickListener: ConfirmListener) : DialogFragment(),
    View.OnClickListener {

    private lateinit var subjectInput: EditText
    private lateinit var desInput: EditText
    private lateinit var confirm: View
    private lateinit var eventModel: EventModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())
        val view: View = LayoutInflater.from(requireContext()).inflate(
            R.layout.dialog_fragment_add_event,
            null,
            false
        )
        subjectInput = view.findViewById(R.id.subject)
        desInput = view.findViewById(R.id.desEvent)
        confirm = view.findViewById(R.id.addEvent)
        eventModel = EventModel() // create event model for send to fragment for save into dataBase

        confirm.setOnClickListener(this)

        dialog.setView(view)
        return dialog.create()
    }


    interface ConfirmListener {
        fun onConfirmClickListener(eventModel: EventModel)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addEvent -> {

                /** subject input validation */
                if (subjectInput.text.toString().trim().isNotEmpty() && desInput.text.toString().trim().isNotEmpty()) {

                    // put in model
                    eventModel.subject = subjectInput.text.toString().trim()
                    eventModel.des = desInput.text.toString().trim()

                } else {
                    subjectInput.error = ""
                    desInput.error = ""
                    return
                }

                // now set real date and Time on model
                val tz = TimeZone.getDefault()
                val hours = Calendar.getInstance(tz).time.hours
                val minute = Calendar.getInstance(tz).time.minutes

                val dt = Utilities.getCurrentShamsidate() + "\n" + hours + ":" + minute
                eventModel.dateTime = dt
                dismiss()
                confirmClickListener.onConfirmClickListener(eventModel)

            }
        }
    }
}