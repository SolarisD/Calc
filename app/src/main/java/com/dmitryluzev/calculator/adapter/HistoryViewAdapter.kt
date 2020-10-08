package com.dmitryluzev.calculator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.model.Record

class HistoryViewAdapter(private val records: List<Record>): RecyclerView.Adapter<HistoryViewAdapter.HistoryViewHolder>()  {
    class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val historyRecord: TextView = view.findViewById(R.id.tv_history_record)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_view_holder, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.historyRecord.text = records[position].op.toString()
    }

    override fun getItemCount(): Int {
        return records.size
    }
}
