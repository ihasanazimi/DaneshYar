package ir.formol.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ir.formol.R
import ir.formol.models.EventModel
import ir.formol.roomDB.RoomDB
import ir.formol.ui.EmptyState
import ir.formol.ui.fragments.dialogFragment.AddEventDialogFragment
import ir.formol.ui.fragments.dialogFragment.EditEventDialogFragment
import ir.formol.utils.adapters.EventsAdapter
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.util.ArrayList

class EventFragment : Fragment() , EmptyState, View.OnClickListener,
    EventsAdapter.EventItemOnclickListener {

    private var list : List<EventModel> = arrayListOf()
    private lateinit var eventAdapter : EventsAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var emptyStateImage : ImageView
    private lateinit var addEventBtn : View
    private lateinit var moreItem : ImageView
    private lateinit var db : RoomDB


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        /** change statusBar color */
        val window: Window = requireActivity().window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.color_gray_darker)


        db = RoomDB.getDataBase(requireContext())
        list = db.daoEvent().allEvents()
        emptyStateImage = view.findViewById(R.id.emptyState)
        addEventBtn = view.findViewById(R.id.floatingActionButton)
        moreItem = view.findViewById(R.id.moreItem)

        /** recyclerview config */
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        eventAdapter = EventsAdapter(this,list as ArrayList<EventModel>)
        recyclerView.adapter = eventAdapter

        // Add Event Listener
        addEventBtn.setOnClickListener(this)
        moreItem.setOnClickListener(this)

    }

    override fun onResume() {

        /** check dataBase for show empty state
         * list.size  => isNotEmpty -> hide empty state
         * list.size => isEmpty -> show empty state*/
        if (list.isEmpty()){
            showEmptyState(true)
        }else {
            showEmptyState(false)
            Handler(Looper.myLooper()!!).postDelayed({
                recyclerView.scrollToPosition(list.size-1)
            },1000)
        }

        super.onResume()

    }

    override fun showEmptyState(show: Boolean) {
        if (show){
            emptyStateImage.visibility = View.VISIBLE // show EMPTY
            recyclerView.visibility = View.INVISIBLE // hide Recycler
        }else {
            emptyStateImage.visibility = View.INVISIBLE // hide EMPTY
            recyclerView.visibility = View.VISIBLE // show Recycler
        }
    }

    override fun onClick(v: View?) {


        when(v?.id){
            R.id.floatingActionButton -> {
                val dialog = AddEventDialogFragment(object : AddEventDialogFragment.ConfirmListener{
                    override fun onConfirmClickListener(eventModel: EventModel) {
                        val uid = db.daoEvent().insert(eventModel)
                        eventModel.id = uid
                        eventAdapter.newItem(eventModel)
                        showEmptyState(false)
                    }
                })
                dialog.show(requireActivity().supportFragmentManager,null)
            }



            R.id.moreItem -> {
                val popUpMenu = PopupMenu(requireContext(),moreItem)
                popUpMenu.menuInflater.inflate(R.menu.event_pop_up_menu,popUpMenu.menu)
                popUpMenu.setOnMenuItemClickListener {

                    if (it?.itemId == R.id.deleteAllItem){
                        db.daoEvent().deleteAll()
                        eventAdapter.clearList()
                        showEmptyState(true)
                        Snackbar.make(requireActivity().findViewById(R.id.eventFragmentContainer),"همه موارد پاک شدند",Snackbar.LENGTH_LONG).show()
                    }
                    return@setOnMenuItemClickListener false
                }
                popUpMenu.show()
            }
            else->{}
        }
    }

    override fun onItemOnClickListener(eventModel: EventModel , position : Int) {
        val dialog = EditEventDialogFragment(object : EditEventDialogFragment.EventListener{

            override fun onDeleteEventListener(eventModel: EventModel) {

                // show message
                Snackbar.make(requireActivity().findViewById(R.id.eventFragmentContainer),"آیتم با موفقیت حذف شد", Snackbar.LENGTH_SHORT).show()

                // delete item from db AND removed from adapter
                db.daoEvent().delete(eventModel)
                eventAdapter.removedItem(eventModel,position)

                // check empty state | check dataBase Items
                if (db.daoEvent().allEvents().isEmpty()){
                    showEmptyState(true)
                }else showEmptyState(false)
            }

            override fun onUpdateEventListener(eventModel: EventModel) {
                // update item into db AND updating on adapter
                db.daoEvent().update(eventModel)
                eventAdapter.updateItem(eventModel,position)
            }

        },eventModel)

        dialog.show(requireActivity().supportFragmentManager,null)
    }

}
