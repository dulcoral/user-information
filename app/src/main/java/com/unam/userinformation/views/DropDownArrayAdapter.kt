package com.unam.userinformation.views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.unam.userinformation.R

abstract class DropDownArrayAdapter<T>(
    context: Context,
    resource: Int,
    items: List<T>
) : ArrayAdapter<T>(context, resource, items) {
    abstract fun doGetView(position: Int, convertView: View?, parent: ViewGroup): View

    final override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return doGetView(position, convertView, parent)
    }

    override fun isEnabled(position: Int) = position != 0

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view as TextView
        if (position == 0) {
            textView.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.grey
                )
            )
        }
        return view
    }
}