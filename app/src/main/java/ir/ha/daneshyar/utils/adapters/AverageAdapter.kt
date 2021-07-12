package ir.ha.daneshyar.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.formol.R
import ir.ha.daneshyar.models.SessionModel

class AverageAdapter(var list: ArrayList<SessionModel>, var callback : OnItemClickListener) : RecyclerView.Adapter<AverageAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_avrage, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        // bind all items
        holder.bind(list[position])

        // item click listener
        holder.item.setOnClickListener{
            callback.onClickAdapterForUpdateModel(list[position],position)
        }
    }



    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    fun removedItem(removedModel: SessionModel, position: Int) {

        if (list[position].id == removedModel.id) {
            list.removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun updateItem(updatedModel: SessionModel, position: Int) {
        list[position] = updatedModel
        notifyItemChanged(position)
    }

    fun newItem(newModel: SessionModel) {
        list.add(list.size,newModel)
        notifyItemInserted(list.size)
    }

    fun setList(newList: List<SessionModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }


    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val unit: TextView = itemView.findViewById(R.id.sessionUnitInput)
        private val name: TextView = itemView.findViewById(R.id.session_name_input)
        val score : TextView = itemView.findViewById(R.id.session_score)
        val item : RelativeLayout = itemView.findViewById(R.id.item)

        fun bind(p: SessionModel) {
            unit.text = p.unit.toString()
            name.text = p.name.toString()
            score.text = p.score.toString()
        }
    }


    interface OnItemClickListener{
        fun onClickAdapterForUpdateModel(sessionModel: SessionModel, position: Int)
    }
}