package ir.ha.daneshyar.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.formol.R
import ir.ha.daneshyar.models.PDFModel

class HomeRecyclerViewAdapter(var list: ArrayList<PDFModel>, var callBack : OnPdfClickListener) :
    RecyclerView.Adapter<HomeRecyclerViewAdapter.VH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.row_main_fragment_recycler_view,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener{
            callBack.onPdfClick(list[position])
        }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val image: ImageView = itemView.findViewById(R.id.rowImage)
        private val title: TextView = itemView.findViewById(R.id.title)


        fun bind(model: PDFModel) {
            model.let {
                image.setImageResource(model.pdfImageForOnlyShow!!)
                title.text = model.imageTitle
            }
        }
    }



    interface OnPdfClickListener {
        fun onPdfClick( pdfModel : PDFModel)
    }
}