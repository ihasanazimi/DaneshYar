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

class DialogFragmentAddUnitStep2(val selectedDay : DayModel, private val callBack: UnitStep2) : DialogFragment(){

    lateinit var continueBtn: View
    lateinit var selectedUnit : NumberPicker
    lateinit var sessionName : EditText
    lateinit var sessionCode : EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // create dialog
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setCancelable(false)
        //create dialog view (layout)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_fragment_add_unit_step2, null, false)
        // initialize dialog views
        continueBtn = view.findViewById(R.id.confirmBtn)
        sessionName = view.findViewById(R.id.session_name)
        sessionCode = view.findViewById(R.id.session_code)
        selectedUnit = view.findViewById(R.id.sessionUnitPicker)

        // listeners
        continueBtn.setOnClickListener {
            if (sessionName.text.toString().trim().isNotEmpty() && sessionCode.text.toString().trim().isNotEmpty()){

                // create new unit
                val newUnit = UnitModel(selectedUnit.value.toShort(),
                    sessionCode.text.toString().trim(),
                    sessionName.text.toString().trim(),
                    "","",0,0,0,0,selectedDay.numberOfWeek!!.toInt())

                callBack.step2Inputs(selectedDay , newUnit)
                dismiss()
            }else {
                sessionName.error = "مقداری وارد کنید"
                sessionCode.error = "مقداری وارد کنید"
            }
        }

        dialog.setView(view)
        return dialog.create()
    }

    interface UnitStep2 {
        fun step2Inputs(selectedDay : DayModel, step2UnitModel: UnitModel)
    }

}