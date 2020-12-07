package com.dmitryluzev.calculator.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.model.Record
import com.dmitryluzev.calculator.view.calculator.CalculatorAdapter
import com.dmitryluzev.calculator.view.history.HistoryAdapter

@BindingAdapter("calculatorList")
fun bindCalculatorRecyclerView(recyclerView: RecyclerView, data: List<Record>?) {
    val adapter = recyclerView.adapter as CalculatorAdapter
    adapter.submitList(data)
    if(!data.isNullOrEmpty()) recyclerView.smoothScrollToPosition(data.lastIndex)
}

@BindingAdapter("historyList")
fun bindHistoryRecyclerView(recyclerView: RecyclerView, data: List<Record>?) {
    val adapter = recyclerView.adapter as HistoryAdapter
    adapter.submitRecordList(data)

}