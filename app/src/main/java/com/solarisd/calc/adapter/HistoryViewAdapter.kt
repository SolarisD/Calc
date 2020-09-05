package com.solarisd.calc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.solarisd.calc.R
import com.solarisd.calc.model.History

class HistoryViewAdapter(private val data: List<History>): RecyclerView.Adapter<HistoryViewAdapter.HistoryViewHolder>()  {
    class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        val historyRecord: TextView = view.findViewById(R.id.tv_history_record)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_view_holder, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.historyRecord.text = data[position].toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
