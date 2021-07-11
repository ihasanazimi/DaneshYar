package ir.formol.ui.fragments.dialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ir.formol.R

class ResultDialogFragment(var units : String, var totalAverage : String , var callBack : OnCancelable) : DialogFragment() {

    private lateinit var closeBtn : View
    private lateinit var unitCountTV : TextView
    private lateinit var totalAverageTV : TextView


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // initialize dialog
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setCancelable(false)
        // inflate dialog layout (view)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_fragment_average_result,null,false)
        // initialize dialog views
        closeBtn = view.findViewById(R.id.closeBtn)
        unitCountTV = view.findViewById(R.id.unitCountTv)
        totalAverageTV = view.findViewById(R.id.totalAverageTv)
        // fill information into views
        unitCountTV.text = units
        totalAverageTV.text = totalAverage
        // listeners
        closeBtn.setOnClickListener {
            dismiss()
            callBack.onCancel()
        }
        // set view for dialog
        dialog.setView(view)
        // return created dialog
        return dialog.create()
    }


    interface OnCancelable{
       fun onCancel()
    }
}