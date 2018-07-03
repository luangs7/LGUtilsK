package br.com.luan2.lgutilsk.utils

import android.text.method.PasswordTransformationMethod
import android.view.View.OnFocusChangeListener
import android.view.animation.AnimationUtils
import android.widget.EditText
import br.com.luan2.lgutilsk.R
import br.com.luan2.lgutilsk.extras.mask.CpfCnpjMask
import br.com.luan2.lgutilsk.extras.mask.MyMaskEditText
import br.com.luan2.lgutilsk.extras.mask.SuperBrazilianTelephoneMask
import java.util.regex.Pattern

/**
 * Created by luan gabriel on 16/04/18.
 */


fun EditText.textTrim(): String {
    return this.text.toString().trim()
}

fun EditText.getTextString(): String {
    return this.text.toString()
}

fun EditText.shakeView() {
    val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
    this.startAnimation(shake)
}

fun EditText.isPasswordValid(maxLenght: Int): Boolean = this.textTrim().length > maxLenght

fun EditText.isEmpty(): Boolean = !text.isNotEmpty()


fun EditText.checkEdittextError(error: String) {
    this.error = error
    this.isFocusableInTouchMode = true
    this.requestFocus()
    this.shakeView()
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
            currentField.checkEdittextError("Esse campo não pode ser vazio!")
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
    val m = Pattern.compile("[A-Z]{3}\\d{4}").matcher(this.getTextString().replace("-", "").toUpperCase())

    return m.find()
}

fun EditText.validCep(): Boolean {
    val pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$")
    val matcher = pattern.matcher(this.getTextString())
    return matcher.find()
}


fun EditText.checkCep(completion: () -> Unit) {
    this.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            if (this.validCep()) {
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


inline infix fun EditText.guard(call: () -> Unit): String? {
    if (!this.isEmpty()) return getTextString()
    else {
        call()
        return null
    }

}

inline fun EditText.guard(rule: Boolean, call: () -> Unit): String? {
    if (!this.isEmpty() && rule) return getTextString()
    else {
        call()
        return null
    }

}