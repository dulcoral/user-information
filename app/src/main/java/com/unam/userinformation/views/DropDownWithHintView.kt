package com.unam.userinformation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.get
import com.unam.userinformation.R
import java.util.LinkedList

class DropDownWithHintView(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var actualPosition: Int = 0
    var listItems: List<String>? = null

    val spinner: Spinner by lazy {
        findViewById<Spinner>(R.id.spinner_drop_down)
    }

    var textSelected: String? = null

    init {
        View.inflate(
            context,
            R.layout.view_spinner_drop_down, this
        )
    }

    fun setupItems(items: List<String>) {
        listItems = items
        val itemsWithHint = LinkedList<String>(items)
        itemsWithHint.push(resources.getString(R.string.select_option))

        val spinnerAdapter = object :
            DropDownArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                itemsWithHint
            ) {
            override fun doGetView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_spinner_item, parent, false)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.text = itemsWithHint[position]
                return view
            }
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) = Unit

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                // Validate the selected position is not hint
                if (isHintPositionAndUpdatePosition(position).not()) {
                    textSelected = items.get(actualPosition)
                }
            }
        }
    }

    fun getSelection(): String? {
        return textSelected
    }

    fun setSelection(position: Int) {
        spinner.setSelection(position + 1)
    }

    fun isHintPositionAndUpdatePosition(position: Int): Boolean {
        return (position == 0).also {
            if (it.not()) {
                actualPosition = position - 1
            }
        }
    }

    fun getItemPosition(): Int = actualPosition
}