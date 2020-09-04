package com.solarisd.calc.adapter

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.solarisd.calc.model.History

class HistoryViewAdapter(private val data: LiveData<List<History>>): RecyclerView.Adapter<HistoryViewAdapter.HistoryViewHolder>()  {
    class HistoryViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
