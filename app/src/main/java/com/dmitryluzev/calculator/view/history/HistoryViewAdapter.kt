package com.dmitryluzev.calculator.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.databinding.VhBinaryOperationBinding
import com.dmitryluzev.calculator.databinding.VhHeaderBinding
import com.dmitryluzev.calculator.model.Record
import com.dmitryluzev.core.operations.BinaryOperation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewAdapter:
    ListAdapter<HistoryViewAdapter.Item, RecyclerView.ViewHolder>(HistoryDiffCallback()){

    companion object{
        const val TYPE_ITEM = 0
        const val TYPE_HEADER = 1
        private val df = SimpleDateFormat.getDateInstance()
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)

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
        if (list.isNullOrEmpty()){
            submitList(listOf())
        } else {
            adapterScope.launch {
                val itemList = mutableListOf<Item>()
                var savedDate = list[0].date
                list.forEach {record ->
                    if (record.date.date != savedDate.date || record.date.month != savedDate.month || record.date.year != savedDate.year){
                        itemList.add(Item.HistoryViewHeader(savedDate))
                        savedDate = record.date
                    }
                    itemList.add(Item.HistoryViewRecord(record))
                }
                itemList.add(Item.HistoryViewHeader(savedDate))
                withContext(Dispatchers.Main){
                    submitList(itemList)
                }
            }
        }
    }

    class RecordViewHolder(val binding: VhBinaryOperationBinding): RecyclerView.ViewHolder(binding.root)

    class HeaderViewHolder(val binding: VhHeaderBinding): RecyclerView.ViewHolder(binding.root)

    class HistoryDiffCallback: DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = when(oldItem){
            is Item.HistoryViewRecord -> {
                val new = newItem as Item.HistoryViewRecord
                oldItem.record.id == new.record.id
                        && oldItem.record.date == new.record.date
                        && oldItem.record.op == new.record.op
            }
            is Item.HistoryViewHeader -> {
                oldItem.date == (newItem as Item.HistoryViewHeader).date
            }
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
            TYPE_ITEM ->{
                val binding = VhBinaryOperationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return RecordViewHolder(binding)
            }
            TYPE_HEADER -> {
                val binding = VhHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HeaderViewHolder(binding)
            }
        }
        throw ClassCastException("Unknown viewType ${viewType}")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is RecordViewHolder -> {
                holder.binding.operation = (getItem(position) as Item.HistoryViewRecord).record.op as BinaryOperation
            }
            is HeaderViewHolder -> {
                holder.binding.tvDate.text = df.format((getItem(position) as Item.HistoryViewHeader).date)
            }
        }
    }
}
