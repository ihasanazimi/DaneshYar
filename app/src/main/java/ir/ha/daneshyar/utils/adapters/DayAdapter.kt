package ir.formol.utils.adapters

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.formol.R
import ir.formol.models.DayModel
import ir.formol.models.EventModel
import java.util.*

class DayAdapter(var list: ArrayList<DayModel> , var callback : OnItemClickListener) : RecyclerView.Adapter<DayAdapter.VH>() {

    lateinit var mParent: ViewParent
    var oldSelectedItem = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        mParent = parent
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.row_day, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener{
            callback.onItemClick(list[position],position)
        }

        holder.itemView.setOnLongClickListener {
            callback.onDelete( list[position] , position)
            return@setOnLongClickListener false
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun clearAll (){
        list.clear()
        notifyDataSetChanged()
    }


    fun setNewList (newDayList : List<DayModel>){
        list.clear()
        list.addAll(newDayList)
        notifyDataSetChanged()
    }

    fun getPosition(dayModel: DayModel) : Int{
        var pos : Int = -1
        for (item in 0 until list.size){
            if (dayModel.numberOfWeek == list[item].numberOfWeek){
                pos = item
                break
            }
        }
        return pos
    }

    fun selectedItem(dayModel: DayModel , position: Int){

        if (oldSelectedItem != -1) {
            for (item in 0 until list.size) {
                list[item].selectionState = false
            }
            notifyDataSetChanged()
        }

        for (item in list){
            if (item.numberOfWeek == dayModel.numberOfWeek){
                item.selectionState = true
                oldSelectedItem = position
                notifyItemChanged(position)
                break
            }
        }
    }


    fun newItem(dayModel: DayModel) {
        list.add(list.size, dayModel)
        notifyItemInserted(list.size)
    }


    fun removedItem(removedModel: DayModel, position: Int) {
        if (list[position].uId == removedModel.uId) {
            list.removeAt(position)
            notifyDataSetChanged()
            notifyItemRangeChanged(position,list.size)
        }
    }

    fun updateItem(updatedModel: DayModel, position: Int) {
        list[position] = updatedModel
        notifyItemChanged(position)
    }


    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val activeShape: View = itemView.findViewById(R.id.activeOvalState)
        private val dayTitle: TextView = itemView.findViewById(R.id.dayTitle)
        private var containerBackground: LinearLayout = itemView.findViewById(R.id.rowDayContainer)

        fun bind(dayModel: DayModel) {
            if (dayModel.selectionState) {
                activeShape.visibility = View.VISIBLE
                dayTitle.text = dayModel.dayTitle
                dayTitle.setTextColor(dayTitle.context.resources.getColor(R.color.color_green))
                containerBackground.setBackgroundResource(R.drawable.shape_day_active)
            } else {
                activeShape.visibility = View.INVISIBLE
                dayTitle.text = dayModel.dayTitle
                dayTitle.setTextColor(dayTitle.context.resources.getColor(R.color.color_waite))
                containerBackground.setBackgroundResource(R.drawable.shape_day_inactive)
            }
        }

    }

    interface OnItemClickListener{
        fun onItemClick( dayModel: DayModel , position: Int)
        fun onDelete(  deleted : DayModel , position: Int )
    }

}