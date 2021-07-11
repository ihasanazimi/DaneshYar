package ir.formol.ui.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import ir.formol.R
import ir.formol.models.SessionModel
import ir.formol.roomDB.RoomDB
import ir.formol.ui.EmptyState
import ir.formol.ui.fragments.dialogFragment.AddSessionDialogFragment
import ir.formol.ui.fragments.dialogFragment.EditSessionDialogFragment
import ir.formol.ui.fragments.dialogFragment.ResultDialogFragment
import ir.formol.utils.adapters.AverageAdapter
import java.util.*

class AverageFragment : Fragment(), View.OnClickListener , EmptyState,
    AverageAdapter.OnItemClickListener, ResultDialogFragment.OnCancelable {

    // views
    private lateinit var calculateAverage: View
    private lateinit var addSession: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var tenCheckBox: CheckBox
    private lateinit var adapter: AverageAdapter
    private lateinit var appbar: AppBarLayout
    private lateinit var des: TextView
    private lateinit var headerImage: ImageView
    private lateinit var emptyStateImageView : ImageView
    private lateinit var headerTitle: TextView
    private lateinit var moreItem : View

    // vars
    var score = 0f
    var unit = 0
    var totalAverage = 0f
    private var tenBoolean : Boolean = false
    private lateinit var scoreList: ArrayList<Float>
    private lateinit var unitList: ArrayList<Short>
    private lateinit var sessionList : ArrayList<SessionModel>

    // others
    private lateinit var db : RoomDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_average,
            container,
            false
        )

        /** initialize views */
        initialize(view)

        getItemFromRoomDB()

        // listeners
        calculateAverage.setOnClickListener(this)
        addSession.setOnClickListener(this)
        moreItem.setOnClickListener(this)
        tenCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

            tenBoolean = isChecked

            if (isChecked){
                tenCheckBox.setTextColor(resources.getColor(R.color.colorAccent))
                tenCheckBox.paintFlags = 0
            }else {
                tenCheckBox.setTextColor(resources.getColor(R.color.color_gray_darker))
                tenCheckBox.paintFlags = tenCheckBox.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }

        return view
    }

    private fun getItemFromRoomDB() {
        sessionList = db.daoSession().allSessions() as ArrayList<SessionModel>
        adapter.setList(sessionList)
        if (sessionList.isEmpty()) {
            showEmptyState(true)
        }else showEmptyState(false)
    }

    private fun initialize(view: View) {
        db = RoomDB.getDataBase(requireContext())

        // views
        recyclerView = view.findViewById(R.id.averageRecyclerView)
        calculateAverage = view.findViewById(R.id.calculateBtn)
        addSession = view.findViewById(R.id.btn_add)
        appbar= view.findViewById(R.id.appbar_avg)
        des = view.findViewById(R.id.des_avg)
        headerImage = view.findViewById(R.id.header_image_avg)
        headerTitle = view.findViewById(R.id.tv1_avg)
        emptyStateImageView = view.findViewById(R.id.emptyState)
        tenCheckBox = view.findViewById(R.id.tenCheckBox)
        moreItem = view.findViewById(R.id.morePopUpMenu)

        // lists
        scoreList = arrayListOf()
        unitList = arrayListOf()
        sessionList = arrayListOf()

        // recyclerview config
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = AverageAdapter(sessionList, this)
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false

        // other functionality
        var isShow = true
        var scrollRange = -1
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange;
            }

            if (scrollRange + verticalOffset == 0) {
                isShow = true;
                headerImage.visibility = View.INVISIBLE;
                des.visibility = View.INVISIBLE;
                headerTitle.visibility = View.VISIBLE;

            } else if (isShow) {
                isShow = false;
                headerImage.visibility = View.VISIBLE;
                des.visibility = View.VISIBLE;
                headerTitle.visibility = View.INVISIBLE;
            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {

            R.id.calculateBtn -> {

                if (tenBoolean) {
                    for (item in adapter.list) {
                        if (item.score!! >= 10) {
                            score += item.unit!! * item.score!!
                            unit += item.unit!!
                        }
                    }

                } else {
                    for (item in adapter.list) {
                        score += item.unit!! * item.score!!
                        unit += item.unit!!
                    }
                }

                totalAverage = score / unit
                val resultDialog = ResultDialogFragment(unit.toString(), totalAverage.toString(),this)
                resultDialog.isCancelable = false
                if (unit != 0 && totalAverage != 0f) {
                    resultDialog.show(requireActivity().supportFragmentManager, null)
                } else {
                    Snackbar.make(
                        requireActivity().findViewById(R.id.fragmentAverage_Container),
                        "چیزی برای محاسبه وجود ندارد",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.btn_add -> {
                val addSessionDialog = AddSessionDialogFragment(object :
                    AddSessionDialogFragment.OnAddSessionResult {
                    override fun addSession(sessionModel: SessionModel) {
                        val uid = db.daoSession().insert(sessionModel)
                        sessionModel.id = uid
                        adapter.newItem(sessionModel)
                        showEmptyState(false)
                    }
                })
                addSessionDialog.show(requireActivity().supportFragmentManager, null)
            }


            R.id.morePopUpMenu -> {

                val popUpMenu = PopupMenu(requireContext(), moreItem)
                popUpMenu.menuInflater.inflate(R.menu.event_pop_up_menu, popUpMenu.menu)
                popUpMenu.setOnMenuItemClickListener {

                    if (it?.itemId == R.id.deleteAllItem) {

                        // reset all -> (adapter - db - variables - emptyImage)
                        db.daoSession().deleteAll()
                        adapter.clearList()
                        showEmptyState(true)
                        resetVars()

                        // show message deleted!
                        Snackbar.make(
                            requireActivity().findViewById(R.id.fragmentAverage_Container),
                            "همه آیتم ها حذف شدند :)",
                            Snackbar.LENGTH_SHORT
                        ).show()

                    }
                    return@setOnMenuItemClickListener false
                }
                popUpMenu.show()

            }
        }
    }

    override fun showEmptyState(show: Boolean) {
        if (show){
            emptyStateImageView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }else {
            emptyStateImageView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onClickAdapterForUpdateModel(sessionModel: SessionModel, position: Int) {
        val dialog = EditSessionDialogFragment(sessionModel,
            object : EditSessionDialogFragment.OnEventSessionResult {
                override fun updateSession(sessionModel: SessionModel) {
                    db.daoSession().update(sessionModel)
                    adapter.updateItem(sessionModel, position)
                }

                override fun deleteSession(sessionModel: SessionModel) {
                    db.daoSession().delete(sessionModel)
                    adapter.removedItem(sessionModel, position)

                    if (adapter.list.isEmpty()) {
                        showEmptyState(true)
                    } else showEmptyState(false)

                    // show message
                    Snackbar.make(
                        requireActivity().findViewById(R.id.fragmentAverage_Container),
                        "آیتم با موفقیت حذف شد",
                        Snackbar.LENGTH_SHORT
                    ).show()

                }
            })
        dialog.show(requireActivity().supportFragmentManager, null)
    }

    override fun onCancel() {
        resetVars()
    }

    private fun resetVars() {
        totalAverage = 0f
        score = 0f
        unit = 0
    }

}
