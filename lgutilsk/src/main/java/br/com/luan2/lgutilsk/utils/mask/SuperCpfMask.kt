package br.com.luan2.lgutilsk.extras.mask

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by Luan on 07/06/17.
 */

object SuperCpfMask {


    private val CPFMask = "###.###.###-##"
    private val CNPJMask = "##.###.###/####-##"

    fun unmask(s: String): String {
        return s.replace("[^0-9]*".toRegex(), "")
    }

    private fun getDefaultMask(str: String): String {
        var defaultMask = CPFMask
        if (str.length == 14) {
            defaultMask = CNPJMask
        }
        return defaultMask
    }

    fun insert(editText: EditText, maskType: MaskType): TextWatcher {
        return object : TextWatcher {

            internal var isUpdating: Boolean = false
            internal var oldValue = ""

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val value = unmask(s.toString())
                val mask: String
                when (maskType) {
                    MaskType.CPF -> mask = CPFMask
                    MaskType.CNPJ -> mask = CNPJMask
//                    else -> mask = getDefaultMask(value)
                }

                var maskAux = ""
                if (isUpdating) {
                    oldValue = value
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && value.length > oldValue.length || m != '#' && value.length < oldValue.length && value.length != i) {
                        maskAux += m
                        continue
                    }

                    try {
                        maskAux += value[i]
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                editText.setText(maskAux)
                editText.setSelection(maskAux.length)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        }
    }


    enum class MaskType {
        CPF,
        CNPJ
    }

}
