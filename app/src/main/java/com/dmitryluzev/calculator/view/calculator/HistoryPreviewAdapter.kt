
package com.dmitryluzev.calculator.view.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.databinding.VhBinaryOperationBinding
import com.dmitryluzev.calculator.model.Record
import com.dmitryluzev.core.operations.BinaryOperation

class HistoryPreviewAdapter():
    ListAdapter<Record, HistoryPreviewAdapter.HistoryPreviewHolder>(DiffCallback())  {

    class HistoryPreviewHolder(val binding: VhBinaryOperationBinding): RecyclerView.ViewHolder(binding.root)

    class DiffCallback: DiffUtil.ItemCallback<Record>(){
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return (oldItem.date == newItem.date
                    && oldItem.op == newItem.op)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPreviewHolder {
        val binding = VhBinaryOperationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryPreviewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryPreviewHolder, position: Int) {
        holder.binding.operation = getItem(position).op as BinaryOperation
    }
}
