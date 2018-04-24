package br.com.luan2.lgutilsk.extras.mask

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by Luan on 17/05/17.
 */

class MoneyTextWatcher(private val editText: EditText) : TextWatcher {

    private var current = ""
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.toString() != current) {
            editText.removeTextChangedListener(this)

            try {
                val replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance(Locale("pt", "BR")).currency.symbol)

                val cleanString = s.toString().replace(replaceable.toRegex(), "")
                val parsed = java.lang.Double.parseDouble(cleanString)
                val formatted = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed / 100)

                current = formatted
                editText.setText(formatted)
                editText.setSelection(formatted.length)
            } catch (ex: Exception) {

            }

            editText.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(s: Editable) {

    }

    companion object {


        fun getCleanString(value: String): String {
            val replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance(Locale("pt", "BR")).currency.symbol)
            return value.toString().replace(replaceable.toRegex(), "")
        }

        fun toFloat(value: String): Double {
            val replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance(Locale("pt", "BR")).currency.symbol)
            val cleanString = value.toString().replace(replaceable.toRegex(), "")
            val parsed = java.lang.Double.parseDouble(cleanString)
            return parsed / 100
        }

        fun getCleanCurrencyString(value: String): String {
            val replaceable = String.format("[%s]", NumberFormat.getCurrencyInstance(Locale("pt", "BR")).currency.symbol)
            return value.toString().replace(replaceable.toRegex(), "")
        }

        fun formatString(value: String): String {
            val decimalFormat = DecimalFormat("#.##")
            decimalFormat.minimumFractionDigits = 2
            decimalFormat.maximumFractionDigits = 2
            val format = decimalFormat.format(java.lang.Float.parseFloat(value).toDouble())

            val replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance(Locale("pt", "BR")).currency.symbol)
            val cleanString = format.toString().replace(replaceable.toRegex(), "")
            val parsed = java.lang.Double.parseDouble(cleanString)

            return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed / 100)
        }
    }

}
