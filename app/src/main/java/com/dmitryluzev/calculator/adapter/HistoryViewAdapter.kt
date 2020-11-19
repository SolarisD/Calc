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
import java.util.*

class HistoryViewAdapter: ListAdapter<HistoryViewAdapter.Item, RecyclerView.ViewHolder>(HistoryDiffCallback())  {
    companion object{
        const val TYPE_ITEM = 0
        const val TYPE_HEADER = 1
    }

    sealed class Item{
        abstract val type: Int
        data class HistoryViewRecord(val record: Record): Item(){
            override val type = TYPE_ITEM
        }
        data class HistoryViewHeader(val date: Date): Item(){
            override val type = TYPE_HEADER
        }
    }

    fun submitRecordList(list: List<Record>?){
        list?.let {
            val itemList = mutableListOf<Item>()
            val header = list[0].date
            itemList.add(Item.HistoryViewHeader(header))
            it.forEach {record ->
                itemList.add(Item.HistoryViewRecord(record))
            }
            submitList(itemList)
            return
        }
        submitList(listOf())
    }

    class RecordViewHolder(view: View): RecyclerView.ViewHolder(view){
        val record: TextView = view.findViewById(R.id.tv_record)
    }

    class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view){
        val date: TextView = view.findViewById(R.id.tv_date)
    }

    class HistoryDiffCallback: DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is Item.HistoryViewRecord -> TYPE_ITEM
            is Item.HistoryViewHeader -> TYPE_HEADER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            TYPE_ITEM->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_history_record, parent, false)
                return RecordViewHolder(view)
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_history_header, parent, false)
                return HeaderViewHolder(view)
            }
        }
        throw ClassCastException("Unknown viewType ${viewType}")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RecordViewHolder -> {
                holder.record.text = (getItem(position) as Item.HistoryViewRecord).record.op.toString()
            }
            is HeaderViewHolder -> {
                holder.date.text = (getItem(position) as Item.HistoryViewHeader).date.toString()
            }
        }
    }
}
