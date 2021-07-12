package ir.ha.daneshyar.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.formol.R
import ir.ha.daneshyar.models.UnitModel

class ShowUnitsAdapter(var list: ArrayList<UnitModel>, var callBack : OnUnitEvents) : RecyclerView.Adapter<ShowUnitsAdapter.VH>() {

    lateinit var mParent : ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        mParent = parent
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_unit_selection, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])

        holder.moreItem.setOnClickListener{
            val popUpMenu = PopupMenu(mParent.context,holder.moreItem)
            popUpMenu.menuInflater.inflate(R.menu.uni_more_item_menu,popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener {

                when(it?.itemId){

                    R.id.editUnit ->{
                        callBack.onEditUnit(list[position],position)
                    }


                    R.id.deleteUnit -> {
                        callBack.onDeleteUnit(list[position],position)
                        notifyDataSetChanged()
                        notifyItemRangeChanged(position,list.size)
                    }
                }

                return@setOnMenuItemClickListener false
            }

            popUpMenu.show()
        }
    }

    fun clearAll (){
        list.clear()
        notifyDataSetChanged()
    }


    fun setNewList (newUnitList : List<UnitModel>){
        list.clear()
        list.addAll(newUnitList)
        notifyDataSetChanged()
    }



    fun newItem(unit: UnitModel) {
        list.add(list.size, unit)
        notifyItemInserted(list.size)
    }


    fun removedItem(removedModel: UnitModel, position: Int) {
        if (list[position].uId == removedModel.uId) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateItem(updatedModel: UnitModel, position: Int) {
        list[position] = updatedModel
        notifyItemChanged(position)
    }



    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sessionCode: TextView = itemView.findViewById(R.id.sessionCode)
        private val sessionName: TextView = itemView.findViewById(R.id.sessionName)
        private val sessionTeacherName: TextView = itemView.findViewById(R.id.teacherName)
        private val sessionClassNumber: TextView = itemView.findViewById(R.id.classNumberORClassName)
        private val sHour: TextView = itemView.findViewById(R.id.sHour)
        private val sMinute: TextView = itemView.findViewById(R.id.sMinute)
        private val eHour: TextView = itemView.findViewById(R.id.eHour)
        private val eMinute: TextView = itemView.findViewById(R.id.eMinute)
        val moreItem: View = itemView.findViewById(R.id.moreItem)

        fun bind(unitModel : UnitModel) {
            sessionCode.text = unitModel.sessionCode
            sessionName.text = unitModel.sessionName + "  (${unitModel.unit}) "
            sessionTeacherName.text = unitModel.teacherName
            sessionClassNumber.text = unitModel.sessionClass
            sHour.text = "${unitModel.sHour}"
            sMinute.text = "${unitModel.sMinute}"
            eHour.text = "${unitModel.eHour}"
            eMinute.text = "${unitModel.eMinute}"
        }
    }



    interface OnUnitEvents{
        fun onEditUnit(updatingUnit: UnitModel, position: Int)
        fun onDeleteUnit(deletingUnit: UnitModel, position: Int)
    }
}
