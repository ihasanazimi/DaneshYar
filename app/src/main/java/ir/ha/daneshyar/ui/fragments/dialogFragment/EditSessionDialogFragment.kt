package ir.formol.ui.fragments.dialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.shawnlin.numberpicker.NumberPicker
import ir.formol.R
import ir.formol.models.SessionModel

class EditSessionDialogFragment(var updateModel : SessionModel ,var callBack : OnEventSessionResult) : DialogFragment() {


    private lateinit var newSession : SessionModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_fragment_edit_session,null,false)
        val sessionName = view.findViewById<EditText>(R.id.sessionNameInput)
        val sessionUnitBuNumberPicker = view.findViewById<NumberPicker>(R.id.unitPicker)
        val sessionScore = view.findViewById<EditText>(R.id.sessionScoreInput)
        val updateBtn = view.findViewById<View>(R.id.confirmBtn)
        val deleteBtn = view.findViewById<View>(R.id.deleteBtn)


        // score text Watcher
        sessionScore.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.i("TAG", "beforeTextChanged: ")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    if (s.toString().trim().toFloat() <= 20f) {
                        updateBtn.isEnabled = true
                        sessionScore.setTextColor(resources.getColor(R.color.colorAccent))
                    } else {
                        updateBtn.isEnabled = false
                        sessionScore.setTextColor(resources.getColor(R.color.color_red))
                        sessionScore.error = "مقدار ورودی بیشتر از حد مجاز می باشد"
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i("TAG", "afterTextChanged: ")
            }

        })

        // fill updated model into view (view)
        sessionName.setText(updateModel.name)
        sessionScore.setText(updateModel.score.toString())
        sessionUnitBuNumberPicker.value = updateModel.unit!!.toInt()

        // confirm listener for add to db and adapter in AverageFragment
        updateBtn.setOnClickListener{
            // validations
            if (sessionName.text.toString().trim().isNotEmpty() &&
                sessionScore.text.toString().trim().isNotEmpty())
            {
                newSession = SessionModel()
                newSession.id = updateModel.id
                newSession.unit = sessionUnitBuNumberPicker.value.toShort()
                newSession.name = sessionName.text.toString()
                newSession.score = sessionScore.text.toString().toFloat()
                // return new Session
                callBack.updateSession(newSession)
                dismiss()
            }else {
                Toast.makeText(requireContext(),"مقادیر ورودی ها را بررسی کنید!",Toast.LENGTH_SHORT).show()
            }
        }

        // delete listener OR delete model
        deleteBtn.setOnClickListener{
            dismiss()
            callBack.deleteSession(updateModel)
        }


        dialog.setView(view)
        return dialog.create()
    }


    interface OnEventSessionResult{
        fun updateSession(sessionModel: SessionModel)
        fun deleteSession(sessionModel: SessionModel)
    }
}