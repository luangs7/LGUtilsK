package br.com.luan2.lgutilsk.extras.mask

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by Luan on 27/11/17.
 */

object CpfCnpjMask {

    private val maskCNPJ = "##.###.###/####-##"
    private val maskCPF = "###.###.###-##"
    lateinit var mListener : CheckType


    interface CheckType{
        fun onChange(isCnpj: Boolean)
    }

    fun unmask(s: String): String {
        return s.replace("[^0-9]*".toRegex(), "")
    }

    fun insert(editText: EditText, listener: CheckType): TextWatcher {
        mListener = listener

        return object : TextWatcher {
            internal var isUpdating: Boolean = false
            internal var old = ""

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                val mask: String
                val defaultMask = getDefaultMask(str)
                if(str.length == 11){
                    mask = maskCPF
                    listener.onChange(false)
                } else if(str.length == 14){
                    mask = maskCNPJ
                    listener.onChange(true)
                } else{
                    mask = defaultMask
                    listener.onChange(false)
                }

                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && str.length > old.length || m != '#' && str.length < old.length && str.length != i) {
                        mascara += m
                        continue
                    }

                    try {
                        mascara += str[i]
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                editText.setText(mascara)
                editText.setSelection(mascara.length)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }

    private fun getDefaultMask(str: String): String {
        var defaultMask = maskCPF
        if (str.length > 11) {
            defaultMask = maskCNPJ
        }
        return defaultMask
    }
}