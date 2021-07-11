package ir.formol.ui.fragments.dialogFragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ir.formol.R
import ir.formol.models.EventModel

class EditEventDialogFragment(private val eventClickListener: EventListener, var updateEventModel: EventModel ) : DialogFragment(),
    View.OnClickListener {

    private lateinit var subjectInput: EditText
    private lateinit var desInput: EditText
    private lateinit var updateBtn: View
    private lateinit var deleteBtn: View
    private lateinit var eventModel: EventModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())
        val view: View = LayoutInflater.from(requireContext()).inflate(
            R.layout.dialog_fragment_edit_event,
            null,
            false
        )

        // initialize
        subjectInput = view.findViewById(R.id.subject)
        desInput = view.findViewById(R.id.desEvent)
        updateBtn = view.findViewById(R.id.addEvent)
        deleteBtn = view.findViewById(R.id.deleteEvent)
        eventModel = EventModel() // create event model for send to fragment for save into dataBase

        // listeners
        updateBtn.setOnClickListener(this)
        deleteBtn.setOnClickListener(this)


        // fill model data into UI
        subjectInput.setText(updateEventModel.subject.toString())
        desInput.setText(updateEventModel.des.toString())
        eventModel.id = updateEventModel.id
        eventModel.subject = updateEventModel.subject
        eventModel.des = updateEventModel.des
        eventModel.dateTime = updateEventModel.dateTime


        dialog.setView(view)
        return dialog.create()
    }



    interface EventListener {
        fun onDeleteEventListener(eventModel: EventModel)
        fun onUpdateEventListener(eventModel: EventModel)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addEvent -> {

                /** subject input validation */
                if (subjectInput.text.toString().trim().isNotEmpty()) {
                    eventModel.subject = subjectInput.text.toString().trim() // save in model
                } else {
                    subjectInput.error = "مقدار ورودی معتبر نمی باشد"
                }

                /** description input validation */
                if (desInput.text.toString().trim().isNotEmpty()) {
                    eventModel.des = desInput.text.toString().trim() // save in model
                } else {
                    desInput.error = "مقدار ورودی معتبر نمی باشد"
                    return
                }


                dismiss()
                eventClickListener.onUpdateEventListener(eventModel)

            }
            R.id.deleteEvent -> {
                dismiss()
                eventClickListener.onDeleteEventListener(eventModel)
            }
        }
    }
}