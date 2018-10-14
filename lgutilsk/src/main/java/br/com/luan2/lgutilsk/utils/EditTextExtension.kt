package br.com.luan2.lgutilsk.utils

import android.content.Context
import android.support.annotation.StringRes
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View.OnFocusChangeListener
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import br.com.luan2.lgutilsk.R
import br.com.luan2.lgutilsk.extras.mask.CpfCnpjMask
import br.com.luan2.lgutilsk.extras.mask.MyMaskEditText
import br.com.luan2.lgutilsk.extras.mask.SuperBrazilianTelephoneMask
import java.util.regex.Pattern

/**
 * Created by luan gabriel on 16/04/18.
 */


enum class MaskTypes{
    CPF,
    CEP,
    PHONE
}

fun EditText.textTrim(): String {
    return this.text.toString().trim()
}

fun EditText.getString(): String {
    return this.text.toString()
}

fun EditText.shakeView() {
    val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
    this.startAnimation(shake)
}


fun EditText.isPasswordValid(maxLenght: Int): Boolean = this.textTrim().length > maxLenght

fun EditText.isEmpty(): Boolean = !text.isNotEmpty()


fun EditText.setEditError(error: String) {
    this.error = error
    this.isFocusableInTouchMode = true
    this.requestFocus()
    this.shakeView()
}

fun EditText.isEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this.textTrim())
            .matches()
}


fun checkEmptyMultiple(editTexts: Array<EditText>): Boolean {

    for (currentField in editTexts) {
        if (currentField.isEmpty()) {
            return true
        }
    }
    return false
}

fun checkEmptyMultipleWithError(editTexts: ArrayList<EditText>): Boolean {
    var hasEmpty: Boolean = false
    val wrongs: ArrayList<EditText> = ArrayList()
    for (currentField in editTexts) {
        if (currentField.isEmpty()) {
            currentField.setEditError("Esse campo não pode ser vazio!")
            hasEmpty = true
            wrongs.add(currentField)
        }
    }
    if (wrongs.reversed().isNotEmpty()) {
        wrongs[0].isFocusableInTouchMode = true
        wrongs[0].requestFocus()
    }
    return hasEmpty
}


fun EditText.isEmailValid(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this.textTrim()).matches()


fun EditText.isLicensePlate(): Boolean {
    val m = Pattern.compile("[A-Z]{3}\\d{4}").matcher(this.getString().replace("-", "").toUpperCase())

    return m.find()
}

fun EditText.isCep(): Boolean {
    val pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$")
    val matcher = pattern.matcher(this.getString())
    return matcher.find()
}

fun EditText.checkCep(completion: () -> Unit) {
    this.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            if (this.isCep()) {
                completion()
            }
        }
    }
}


fun EditText.passwordToggledVisible() {
    val selection = selectionStart
    transformationMethod = if (transformationMethod == null) PasswordTransformationMethod() else null
    setSelection(selection)
}

fun EditText.addCPFMask() = this.addTextChangedListener(CpfCnpjMask.insert(this))


fun EditText.addCEPMask() = this.addTextChangedListener(MyMaskEditText(this, "#####-###"))


fun EditText.addPhoneMask() = this.addTextChangedListener(SuperBrazilianTelephoneMask(this))

fun EditText.getDefaultValue(defaultValue: String): String {
    return if (text.isNotEmpty())
        text.toString().trim { it <= ' ' }
    else
        defaultValue

}

fun EditText.addMask(type:MaskTypes) {
    //TODO: switch case
    if (type == MaskTypes.CEP){
        this.addTextChangedListener(MyMaskEditText(this, "#####-###"))
    }else if(type == MaskTypes.CPF){

    }
    else if(type == MaskTypes.PHONE){
        this.addTextChangedListener(SuperBrazilianTelephoneMask(this))
    }
}

fun EditText.focus() {
    if (hasFocus()) {
        setSelection(text.length)
    }
}

fun EditText.afterTextChanged(afterTextChanged: (Editable?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}

fun EditText.beforeTextChanged(beforeTextChanged: (CharSequence?, Int, Int, Int) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}

fun EditText.onTextChanged(onTextChanged: (CharSequence?, Int, Int, Int) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s, start, before, count)
        }

    })
}


fun EditText.requestFocusAndKeyboard() {
    requestFocus()
    val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.clearFocusAndKeyboard() {
    clearFocus()
    val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

infix fun TextView.set(@StringRes id: Int) {
    setText(id)
}

infix fun TextView.set(text: String) {
    setText(text)
}

infix fun TextView.set(text: Spannable) {
    setText(text)
}


inline infix fun EditText.guard(call: () -> Unit): String? {
    if (!this.isEmpty()) return getString()
    else {
        call()
        return null
    }

}

inline fun EditText.guard(rule: Boolean, call: () -> Unit): String? {
    if (!this.isEmpty() && rule) return getString()
    else {
        call()
        return null
    }

}

