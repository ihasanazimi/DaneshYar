package ir.ha.daneshyar.ui.fragments.dialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.shawnlin.numberpicker.NumberPicker
import ir.formol.R
import ir.ha.daneshyar.models.DayModel
import ir.ha.daneshyar.models.UnitModel

class DialogFragmentAddUnitStep3(var selectedDay: DayModel, var step2UnitModel : UnitModel, private val callBack: UnitStep3) : DialogFragment(){


    private lateinit var continueBtn : View
    lateinit var teacherName : EditText
    lateinit var sessionClassOrClassLocation : EditText
    lateinit var minuteStartTimeNumberPicker : NumberPicker
    lateinit var hoursStartTimeNumberPicker: NumberPicker
    lateinit var minuteEndTimeNumberPicker : NumberPicker
    lateinit var hoursEndTimeNumberPicker : NumberPicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // create dialog
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setCancelable(false)
        //create dialog view (layout)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_fragment_add_unit_step3, null, false)
        // initialize dialog views
        continueBtn = view.findViewById<View>(R.id.confirmBtn)
        teacherName = view.findViewById(R.id.session_teacher)
        sessionClassOrClassLocation = view.findViewById(R.id.session_class)
        minuteStartTimeNumberPicker = view.findViewById(R.id.minuteStartTimeNumberPicker)
        hoursStartTimeNumberPicker = view.findViewById(R.id.hoursStartTimeNumberPicker)
        minuteEndTimeNumberPicker = view.findViewById(R.id.minuteEndTimeNumberPicker)
        hoursEndTimeNumberPicker = view.findViewById(R.id.hoursEndTimeNumberPicker)


        // listeners
        continueBtn.setOnClickListener {
            if (sessionClassOrClassLocation.text.toString().trim().isNotEmpty() && teacherName.text.toString().trim().isNotEmpty()){

                // create step3 unit model
                val unitStep3Model = UnitModel(
                    step2UnitModel.unit!!.toShort(),
                    step2UnitModel.sessionCode.toString(),
                    step2UnitModel.sessionName.toString(),
                    sessionClassOrClassLocation.text.toString().trim(),
                    teacherName.text.toString().trim(),
                    hoursStartTimeNumberPicker.value,
                    minuteStartTimeNumberPicker.value,
                    hoursEndTimeNumberPicker.value,
                    minuteEndTimeNumberPicker.value,
                    selectedDay.numberOfWeek!!.toInt()
                )

                callBack.unitStep3(selectedDay,step2UnitModel,unitStep3Model)
                dismiss()
            }else {
                sessionClassOrClassLocation.error = "مقداری وارد نشده است!"
                teacherName.error = "مقداری وارد نشده است!"
            }
        }

        dialog.setView(view)
        return dialog.create()
    }


    interface UnitStep3 {
        fun unitStep3(selectedDay: DayModel, step2UnitModel : UnitModel, step3UnitModel : UnitModel)
    }

}