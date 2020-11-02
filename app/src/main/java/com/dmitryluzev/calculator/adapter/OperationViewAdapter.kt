package com.dmitryluzev.calculator.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.core.operations.Operation

class OperationViewAdapter(private val records: List<Operation>): RecyclerView.Adapter<OperationViewAdapter.HistoryViewHolder>()  {
    class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val operation: TextView = view.findViewById(R.id.tv_operation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.operation_view_holder, parent, false)
        return HistoryViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.operation.text = records[position].toString()
        if (position == 1) {
            holder.operation.setTextColor(ContextCompat.getColor(holder.operation.context, R.color.color_fore_e))
        }
    }

    override fun getItemCount(): Int {
        return records.size
    }
}
