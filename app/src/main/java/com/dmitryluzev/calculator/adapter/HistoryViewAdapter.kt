package com.dmitryluzev.calculator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.model.Record

class HistoryViewAdapter: ListAdapter<Record, HistoryViewAdapter.HistoryViewHolder>(HistoryDiffCallback())  {

    class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val historyRecord: TextView = view.findViewById(R.id.tv_history_record)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.historyRecord.text = getItem(position).op.toString()
    }
}

class HistoryDiffCallback: DiffUtil.ItemCallback<Record>(){
    override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
        return (oldItem.op::class == newItem.op::class
                && oldItem.op.a == newItem.op.a
                && oldItem.op.result == newItem.op.result)
    }
}
