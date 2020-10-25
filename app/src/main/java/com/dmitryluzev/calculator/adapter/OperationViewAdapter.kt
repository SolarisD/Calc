package com.dmitryluzev.calculator.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.operations.Operation

class OperationViewAdapter(private val records: List<Operation>): RecyclerView.Adapter<OperationViewAdapter.HistoryViewHolder>()  {
    class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val operation: TextView = view.findViewById(R.id.tv_operation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.operation_view_holder, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.operation.text = records[position].toString()
    }

    override fun getItemCount(): Int {
        return records.size
    }
}
