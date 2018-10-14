package br.com.luan2.lgutilsk.utils

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner


/**
 * Created by luan silva on 05/07/18.
 */

fun Spinner.create(@LayoutRes itemLayout: Int, @IdRes textViewId: Int, items: Array<String>,
                   onItemSelected: (String, Int) -> Unit = { _, _ -> }) {
    val aAdapter = ArrayAdapter<String>(context, itemLayout, textViewId, items)
    adapter = aAdapter
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(items[position], position)
        }
    }
}

fun Spinner.create(@LayoutRes itemLayout: Int, @IdRes textViewId: Int, items: MutableList<String>,
                   onItemSelected: (String, Int) -> Unit = { _, _ -> }) {
    val aAdapter = ArrayAdapter<String>(context, itemLayout, textViewId, items)
    adapter = aAdapter
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(items[position], position)
        }
    }
}

fun AutoCompleteTextView.create(@LayoutRes itemLayout: Int, @IdRes textViewId: Int, items: Array<String>,
                                onItemSelected: (String, Int) -> Unit = { _, _ -> }) {
    val adapter = ArrayAdapter<String>(context, itemLayout, textViewId, items)
    setAdapter(adapter)
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(items[position], position)
        }
    }
}

fun AutoCompleteTextView.create(@LayoutRes itemLayout: Int, @IdRes textViewId: Int, items: MutableList<String>,
                                onItemSelected: (String, Int) -> Unit = { _, _ -> }) {
    val adapter = ArrayAdapter<String>(context, itemLayout, textViewId, items)
    setAdapter(adapter)
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected(items[position], position)
        }
    }
}
