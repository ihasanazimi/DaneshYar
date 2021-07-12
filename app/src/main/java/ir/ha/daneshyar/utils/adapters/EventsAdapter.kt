package ir.ha.daneshyar.utils.adapters

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.formol.R
import ir.ha.daneshyar.models.EventModel
import java.util.*

class EventsAdapter(
    val callBack: EventItemOnclickListener,
    var list: ArrayList<EventModel> = arrayListOf()
) : RecyclerView.Adapter<EventsAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_event, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])

        holder.item.setOnClickListener {
            callBack.onItemOnClickListener(list[position], position)
        }
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    fun removedItem(removedModel: EventModel, position: Int) {

        if (list[position].id == removedModel.id) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateItem(updatedModel: EventModel, position: Int) {
        list[position] = updatedModel
        notifyItemChanged(position)
    }

    fun newItem(newModel: EventModel) {
        list.add(list.size, newModel)
        notifyItemInserted(list.size)
        Handler(Looper.myLooper()!!).postDelayed({
            notifyDataSetChanged()
        }, 600)
    }

    fun setList(newList: List<EventModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    //-------------------------------------------------------------------------------------------------------------
    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var subjectTV: TextView = itemView.findViewById(R.id.subject)
        var desTV: TextView = itemView.findViewById(R.id.des)
        var dateTime: TextView = itemView.findViewById(R.id.dateTimeTv)
        val item : RelativeLayout = itemView.findViewById(R.id.eventItemContainer)


        fun bind(eventModel: EventModel) {
            subjectTV.text = eventModel.subject
            desTV.text = eventModel.des
            dateTime.text = eventModel.dateTime
        }


    }


    interface EventItemOnclickListener {
        fun onItemOnClickListener(eventModel: EventModel, position: Int)
    }

}