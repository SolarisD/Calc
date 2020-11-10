package com.dmitryluzev.calculator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.R
import com.dmitryluzev.core.operations.base.Operation

class HistoryViewAdapter(): RecyclerView.Adapter<HistoryViewAdapter.HistoryViewHolder>()  {
    var records: List<Operation> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val historyRecord: TextView = view.findViewById(R.id.tv_history_record)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.historyRecord.text = records[position].toString()
    }

    override fun getItemCount(): Int {
        return records.size
    }
}
