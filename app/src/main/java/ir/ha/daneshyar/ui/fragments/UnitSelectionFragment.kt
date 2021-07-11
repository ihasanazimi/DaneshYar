package ir.formol.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import ir.formol.R
import ir.formol.models.DayModel
import ir.formol.models.UnitModel
import ir.formol.roomDB.RoomDB
import ir.formol.ui.EmptyState
import ir.formol.ui.fragments.dialogFragment.DialogFragmentAddUnitStep1
import ir.formol.ui.fragments.dialogFragment.DialogFragmentAddUnitStep2
import ir.formol.ui.fragments.dialogFragment.DialogFragmentAddUnitStep3
import ir.formol.ui.fragments.dialogFragment.DialogFragmentUpdateUnit
import ir.formol.utils.adapters.DayAdapter
import ir.formol.utils.adapters.ShowUnitsAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper


class UnitSelectionFragment : Fragment(), DayAdapter.OnItemClickListener,
    ShowUnitsAdapter.OnUnitEvents, View.OnClickListener, EmptyState {

    private lateinit var addUnit: View
    private lateinit var dayRecyclerView: RecyclerView
    private lateinit var unitsRecyclerView: RecyclerView
    private lateinit var appbar: AppBarLayout
    private lateinit var emptyStateImageView: ImageView
    private lateinit var headerTitle: TextView
    private lateinit var moreItem: View


    private lateinit var dayAdapter: DayAdapter
    private lateinit var unitsAdapter: ShowUnitsAdapter
    private lateinit var dialogStep1: DialogFragmentAddUnitStep1
    private lateinit var dialogStep2: DialogFragmentAddUnitStep2
    private lateinit var dialogStep3: DialogFragmentAddUnitStep3
    private lateinit var db: RoomDB

    private var dayList = arrayListOf<DayModel>()
    private var unitList = arrayListOf<UnitModel>()
    private var activeNumberOfWeek: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_unit_selection, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** initialize layout views */
        initialize(view)


        addUnit.setOnClickListener(this)
        moreItem.setOnClickListener(this)

    }

    private fun initialize(view: View) {

        /** change statusBar color */
        val window: Window = requireActivity().window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.color_green2)


        db = RoomDB.getDataBase(requireContext())
        addUnit = view.findViewById(R.id.addUnitFloatActionButton)
        appbar = view.findViewById(R.id.appbar)
        headerTitle = view.findViewById(R.id.header)
        moreItem = view.findViewById(R.id.morePopUpMenu)
        dayRecyclerView = view.findViewById(R.id.dayRecyclerView)
        unitsRecyclerView = view.findViewById(R.id.unitRecyclerView)
        emptyStateImageView = view.findViewById(R.id.emptyState)

        dayRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        unitsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        dayAdapter = DayAdapter(dayList, this@UnitSelectionFragment)
        unitsAdapter = ShowUnitsAdapter(unitList, this@UnitSelectionFragment)
        dayRecyclerView.adapter = dayAdapter
        unitsRecyclerView.adapter = unitsAdapter

        OverScrollDecoratorHelper.setUpOverScroll(dayRecyclerView, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)

        val dl = db.daoDay().allDays() as ArrayList<DayModel>
        dayAdapter.setNewList(dl)
        if (dl.isNotEmpty()) {
            showEmptyState(false)
            activeNumberOfWeek = dl[0].numberOfWeek!!
            unitList =
                db.daoUnitSelection().searchById(dl[0].numberOfWeek!!) as ArrayList<UnitModel>
            unitsAdapter.setNewList(unitList)
            dayAdapter.selectedItem(dl[0], 0)
        } else {
            Toast.makeText(requireContext(), "برای شروع بر روی + کلیک کنید", Toast.LENGTH_LONG).show()
            showEmptyState(true)
        }


        // other functionality
        var isShow = true
        var scrollRange = -1
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange;
            }

            if (scrollRange + verticalOffset == 0) {
                isShow = true;
                headerTitle.visibility = View.VISIBLE

            } else if (isShow) {
                isShow = false;
                headerTitle.visibility = View.INVISIBLE
            }

        })
    }

    // day adapter on item click listener
    override fun onItemClick(dayModel: DayModel, position: Int) {
        showEmptyState(false)
        activeNumberOfWeek = dayModel.numberOfWeek!!
        dayAdapter.selectedItem(dayModel, position)
        unitsAdapter.setNewList(db.daoUnitSelection().searchById(dayModel.numberOfWeek!!))
    }
    // day adapter on item Long click listener
    override fun onDelete(deleted: DayModel, position: Int) {

        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("تایید درخواست")
        dialog.setIcon(R.drawable.ic_baseline_info_24)
        dialog.setMessage("برای حذف روز ${deleted.dayTitle} اطمینان دارید ؟ ")
        dialog.setPositiveButton("حذف") { mDialog, which ->

            db.daoDay().delete(deleted) // delete from dataBase
            dayAdapter.removedItem(deleted,position) // delete from adapter
            val deletedListItemsFromDeletedDay = db.daoUnitSelection().searchById(deleted.numberOfWeek!!.toLong())
            for (unit in deletedListItemsFromDeletedDay){
                db.daoUnitSelection().delete(unit)
            }
            unitsAdapter.clearAll()
            mDialog.dismiss() // close dialog
        }

        dialog.setNeutralButton("لغو") {mDialog , which ->
            mDialog.dismiss()
        }

        dialog.show()
    }


    // units adapter on item click listener (UPDATE ITEM)
    override fun onEditUnit(updatingUnit: UnitModel, position: Int) {
        val dialog = DialogFragmentUpdateUnit(
            updatingUnit,
            object : DialogFragmentUpdateUnit.OnUpdateResult {
                override fun inResUpdate(updatedUnit: UnitModel) {
                    db.daoUnitSelection().update(updatedUnit)
                    unitsAdapter.updateItem(updatedUnit, position)
                    Snackbar.make(
                        requireActivity().findViewById(R.id.unitSelectionContainerFragment),
                        "آیتم مورد نظر ویرایش شد",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })
        dialog.show(requireActivity().supportFragmentManager, null)
    }

    // units adapter on item click listener (DELETE ITEM)
    override fun onDeleteUnit(deletingUnit: UnitModel, position: Int) {
        db.daoUnitSelection().delete(deletingUnit)
        unitsAdapter.removedItem(deletingUnit, position)
        Snackbar.make(
            requireActivity().findViewById(R.id.unitSelectionContainerFragment),
            "حذف شد!",
            Snackbar.LENGTH_SHORT
        ).show()

        if (db.daoUnitSelection().searchById(deletingUnit.numberOfWeek!!.toLong()).isEmpty()){
            unitsAdapter.clearAll()
            val dl = db.daoDay().searchById(deletingUnit.numberOfWeek!!.toLong())
            db.daoDay().delete(dl[0])
            dayAdapter.removedItem(dl[0], dayAdapter.getPosition(dl[0]))
            showEmptyState(true)
        }

    }



    override fun onClick(v: View?) {

        when (v?.id) {


            R.id.addUnitFloatActionButton -> {
                dialogStep1 = DialogFragmentAddUnitStep1(object :
                    DialogFragmentAddUnitStep1.UnitSelectionStep1 {
                    override fun selectedDay(dayModel: DayModel) {
                        dialogStep2 = DialogFragmentAddUnitStep2(
                            dayModel,
                            object : DialogFragmentAddUnitStep2.UnitStep2 {
                                override fun step2Inputs(
                                    selectedDay: DayModel,
                                    step2UnitModel: UnitModel
                                ) {
                                    dialogStep3 = DialogFragmentAddUnitStep3(
                                        selectedDay,
                                        step2UnitModel,
                                        object : DialogFragmentAddUnitStep3.UnitStep3 {
                                            override fun unitStep3(
                                                selectedDay: DayModel,
                                                step2UnitModel: UnitModel,
                                                step3UnitModel: UnitModel
                                            ) {

                                                selectedDay.selectionState = false

                                                val unitUid = db.daoUnitSelection().insert(
                                                    step3UnitModel
                                                )
                                                step3UnitModel.uId = unitUid

                                                val newUnitsList = db.daoUnitSelection().searchById(
                                                    selectedDay.numberOfWeek!!
                                                )
                                                unitsAdapter.setNewList(newUnitsList)

                                                if (db.daoDay()
                                                        .searchById(selectedDay.numberOfWeek!!.toLong())
                                                        .isEmpty()
                                                ) {
                                                    val uId = db.daoDay().insert(selectedDay)
                                                    selectedDay.uId = uId
                                                    dayAdapter.setNewList(db.daoDay().allDays())
                                                    dayAdapter.selectedItem(
                                                        selectedDay,
                                                        dayList.size - 1
                                                    )
                                                } else {
                                                    dayAdapter.selectedItem(
                                                        selectedDay, dayAdapter.getPosition(
                                                            selectedDay
                                                        )
                                                    )
                                                }

                                                showEmptyState(false)
                                            }

                                        })
                                    dialogStep3.isCancelable = false
                                    dialogStep3.show(requireActivity().supportFragmentManager, null)
                                }
                            })
                        dialogStep2.isCancelable = false
                        dialogStep2.show(requireActivity().supportFragmentManager, null)
                    }
                })
                dialogStep1.show(requireActivity().supportFragmentManager, null)
            }


            R.id.morePopUpMenu -> {

                val popUp = PopupMenu(requireContext(), moreItem)
                popUp.menuInflater.inflate(R.menu.unit_selection_menu_items, popUp.menu)
                popUp.setOnMenuItemClickListener {

                    when (it?.itemId) {

                        R.id.deleteAllDaysAndAllUnits -> {
                            db.daoDay().deleteAll()
                            db.daoUnitSelection().deleteAll()
                            dayAdapter.clearAll()
                            unitsAdapter.clearAll()
                            Snackbar.make(
                                requireActivity().findViewById(R.id.unitSelectionContainerFragment),
                                "همه موارد حذف شدند!", Snackbar.LENGTH_LONG
                            ).show()
                            showEmptyState(true)
                        }

                    }

                    return@setOnMenuItemClickListener false
                }

                popUp.show()
            }
        }

    }

    override fun showEmptyState(show: Boolean) {
        if (show) {
            emptyStateImageView.visibility = View.VISIBLE
        } else {
            emptyStateImageView.visibility = View.INVISIBLE
        }
    }
}
