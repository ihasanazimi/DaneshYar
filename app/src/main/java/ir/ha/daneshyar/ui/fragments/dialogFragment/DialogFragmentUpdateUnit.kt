package ir.ha.daneshyar.ui.fragments.dialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.shawnlin.numberpicker.NumberPicker
import ir.formol.R
import ir.ha.daneshyar.models.UnitModel

class DialogFragmentUpdateUnit(private val updatingUnitModel : UnitModel, val callBack : OnUpdateResult) : DialogFragment() {

    lateinit var sessionNameInput : EditText
    lateinit var sessionCodeInput : EditText
    lateinit var sessionTeacherNameInput : EditText
    lateinit var sessionClassInput : EditText
    lateinit var sessionUnitPicker : NumberPicker
    lateinit var minuteStartTimeNumberPicker : NumberPicker
    lateinit var hoursStartTimeNumberPicker : NumberPicker
    lateinit var minuteEndTimeNumberPicker : NumberPicker
    lateinit var hoursEndTimeNumberPicker : NumberPicker
    lateinit var confirmBtn : View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //create new dialog (alert dialog)
        val dialog = AlertDialog.Builder(requireContext())

        // create dialog layout
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_update_unit,null,false)

        // initialize dialog views
        sessionNameInput = view.findViewById(R.id.session_name)
        sessionCodeInput = view.findViewById(R.id.session_code)
        sessionTeacherNameInput = view.findViewById(R.id.session_teacher)
        sessionClassInput = view.findViewById(R.id.session_class)
        sessionUnitPicker = view.findViewById(R.id.sessionUnitPicker)
        minuteStartTimeNumberPicker = view.findViewById(R.id.minuteStartTimeNumberPicker)
        hoursStartTimeNumberPicker = view.findViewById(R.id.hoursStartTimeNumberPicker)
        minuteEndTimeNumberPicker = view.findViewById(R.id.minuteEndTimeNumberPicker)
        hoursEndTimeNumberPicker = view.findViewById(R.id.hoursEndTimeNumberPicker)
        confirmBtn = view.findViewById(R.id.confirmBtn)

        // show updating model in dialog
        sessionNameInput.setText(updatingUnitModel.sessionName)
        sessionCodeInput.setText(updatingUnitModel.sessionCode)
        sessionTeacherNameInput.setText(updatingUnitModel.teacherName)
        sessionClassInput.setText(updatingUnitModel.sessionClass)
        sessionUnitPicker.value = updatingUnitModel.unit!!.toInt()
        hoursStartTimeNumberPicker.value = updatingUnitModel.sHour!!
        minuteStartTimeNumberPicker.value = updatingUnitModel.sMinute!!
        hoursEndTimeNumberPicker.value = updatingUnitModel.eHour!!
        minuteEndTimeNumberPicker.value = updatingUnitModel.eMinute!!



        // listener
        confirmBtn.setOnClickListener{

            // validation inputs (isNotEmpty)
            if (sessionNameInput.text.toString().trim().isNotEmpty() &&
                    sessionCodeInput.text.toString().trim().isNotEmpty() &&
                    sessionTeacherNameInput.text.toString().trim().isNotEmpty() &&
                    sessionClassInput.text.toString().trim().isNotEmpty()){

                val updatedUnitModel = UnitModel() // create new unit model (initialize)
                updatedUnitModel.apply {
                    this.uId = updatingUnitModel.uId
                    this.numberOfWeek = updatingUnitModel.numberOfWeek
                    this.sessionName = sessionNameInput.text.toString().trim()
                    this.sessionCode = sessionCodeInput.text.toString().trim()
                    this.teacherName = sessionTeacherNameInput.text.toString().trim()
                    this.sessionClass = sessionClassInput.text.toString().trim()
                    this.unit = sessionUnitPicker.value.toShort()
                    this.sHour = hoursStartTimeNumberPicker.value
                    this.sMinute = minuteStartTimeNumberPicker.value
                    this.eHour = hoursEndTimeNumberPicker.value
                    this.eMinute = minuteEndTimeNumberPicker.value
                }
                callBack.inResUpdate(updatedUnitModel) // result
                dismiss() // close dialog window
            }else {
                Toast.makeText(requireContext(),"لطفا همه مقادیر را وارد نمائید.",Toast.LENGTH_SHORT).show()
            }

        }

        dialog.setView(view)
        return dialog.create()
    }


    interface OnUpdateResult{
        fun inResUpdate(updatedUnit: UnitModel)
    }
}