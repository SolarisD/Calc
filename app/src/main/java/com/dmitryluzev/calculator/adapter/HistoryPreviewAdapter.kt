package com.dmitryluzev.calculator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.model.Record

class HistoryPreviewAdapter(private val onClick: () -> Unit): ListAdapter<Record, HistoryPreviewAdapter.HistoryPreviewHolder>(HistoryDiffCallback())  {

    class HistoryPreviewHolder(view: View): RecyclerView.ViewHolder(view){
        val historyRecord: TextView = view.findViewById(R.id.tv_history_record)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPreviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_history, parent, false)
        return HistoryPreviewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryPreviewHolder, position: Int) {
        holder.historyRecord.text = getItem(position).op.toString()
        holder.historyRecord.setOnClickListener { onClick() }
    }
}
