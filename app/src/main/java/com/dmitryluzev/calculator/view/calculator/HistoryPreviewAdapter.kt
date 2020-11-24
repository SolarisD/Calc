package com.dmitryluzev.calculator.view.calculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.model.Record

class HistoryPreviewAdapter(private val onClick: () -> Unit): ListAdapter<Record, HistoryPreviewAdapter.HistoryPreviewHolder>(
    DiffCallback()
)  {

    class HistoryPreviewHolder(view: View): RecyclerView.ViewHolder(view){
        val record: TextView = view.findViewById(R.id.tv_record)
    }

    class DiffCallback: DiffUtil.ItemCallback<Record>(){
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return (oldItem.date == newItem.date
                    && oldItem.op == newItem.op)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPreviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_preview_history_record, parent, false)
        return HistoryPreviewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryPreviewHolder, position: Int) {
        holder.record.text = getItem(position).op.toString()
        holder.record.setOnClickListener { onClick() }
    }
}
