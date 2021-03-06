package com.dmitryluzev.calculator.view

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dmitryluzev.calculator.R
import com.dmitryluzev.calculator.model.Record
import com.dmitryluzev.calculator.view.calculator.CalculatorAdapter
import com.dmitryluzev.calculator.view.history.HistoryAdapter
import com.dmitryluzev.core.buffer.Converter
import com.dmitryluzev.core.operations.*

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

@BindingAdapter("bindOperationTag")
fun bindOperationTag(textView: TextView, operation: Operation?){
    when(operation){
        is Add -> textView.text = textView.context.getString(R.string.add_tag)
        is Subtract -> textView.text = textView.context.getString(R.string.subtract_tag)
        is Multiply -> textView.text = textView.context.getString(R.string.multiply_tag)
        is Divide -> textView.text = textView.context.getString(R.string.divide_tag)
        is Percent -> textView.text = textView.context.getString(R.string.percent_tag)
    }
}

@BindingAdapter("bindOperation")
fun bindOperation(textView: TextView, operation: Operation?){
    textView.visibility = if (operation != null && operation.result() == null) View.VISIBLE else View.GONE
    
    when(operation){
        is BinaryOperation -> {
            val tag = when(operation){
                is Add -> textView.context.getString(R.string.add_tag)
                is Subtract -> textView.context.getString(R.string.subtract_tag)
                is Multiply -> textView.context.getString(R.string.multiply_tag)
                is Divide -> textView.context.getString(R.string.divide_tag)
                is Percent -> textView.context.getString(R.string.percent_tag)
                else -> ""
            }
            val text = StringBuilder()
            operation.a?.let {
                text.append(Converter.doubleToString(it))
                text.append(tag)
            }
            operation.b?.let { text.append(Converter.doubleToString(it)) }
            operation.result()?.let { text.append(" = "); text.append(Converter.doubleToString(it)) }

            textView.text = text.toString()
        }
    }
}

@BindingAdapter("fromDouble")
fun bindValueToTextView(textView: TextView, data: Double?){
    textView.text = Converter.doubleToString(data) ?: ""
}