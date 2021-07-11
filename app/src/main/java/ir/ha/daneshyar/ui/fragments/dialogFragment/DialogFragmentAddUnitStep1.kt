package ir.formol.ui.fragments.dialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.formol.R
import ir.formol.models.DayModel
import ir.formol.roomDB.RoomDB
import ir.formol.utils.adapters.DayAdapter
import ir.formol.utils.adapters.DayAdapter2
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.util.ArrayList

class DialogFragmentAddUnitStep1(private val callBack: UnitSelectionStep1) : DialogFragment(),
    DayAdapter2.OnItemClickListener {

    lateinit var adapter: DayAdapter2
    private lateinit var selectedDay: DayModel
    lateinit var continueBtn: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // create dialog
        val dialog = AlertDialog.Builder(requireContext())
        //create dialog view (layout)
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_fragment_add_unit_step1, null, false)
        // initialize dialog views
        continueBtn = view.findViewById<View>(R.id.confirmBtn)
        val recyclerViewSelectDay = view.findViewById<RecyclerView>(R.id.recyclerviewSelectDay)

        // recyclerview and his adapter -> initialized
        adapter = DayAdapter2(days(), this)
        recyclerViewSelectDay.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerViewSelectDay.adapter = adapter
        OverScrollDecoratorHelper.setUpOverScroll(recyclerViewSelectDay,OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)

        // listeners AND save day into db
        continueBtn.setOnClickListener {
            if (selectedDay != null) {
//                db.daoDay().insert(selectedDay)
                callBack.selectedDay(selectedDay)
                dismiss()
            } else Toast.makeText(
                requireContext(),
                "روزِ هفته شما انتخاب نشده است!",
                Toast.LENGTH_SHORT
            ).show()
        }

        dialog.setView(view)
        return dialog.create()
    }

    private fun days(): ArrayList<DayModel> {
        val days = arrayListOf<DayModel>()
        days.add(DayModel(0, "شنبه", false))
        days.add(DayModel(1, "یکشنبه", false))
        days.add(DayModel(2, "دوشنبه", false))
        days.add(DayModel(3, "سه شنبه", false))
        days.add(DayModel(4, "چهارشنبه", false))
        days.add(DayModel(5, "پنجشنبه", false))
        days.add(DayModel(6, "جمعه", false))
        return days
    }

    // days adapter on item click listener
    override fun onItemClick(dayModel: DayModel, position: Int) {
        if (!dayModel.selectionState) {
            continueBtn.isEnabled = true
            selectedDay = DayModel()
            selectedDay = dayModel
            adapter.selectedItem(selectedDay, position)
        }else Toast.makeText(
            requireContext(),
            "این روز انتخاب شده است",
            Toast.LENGTH_SHORT
        ).show()
    }


    interface UnitSelectionStep1 {
        fun selectedDay(dayModel: DayModel)
    }

}