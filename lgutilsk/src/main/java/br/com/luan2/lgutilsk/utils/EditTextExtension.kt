package br.com.luan2.lgutilsk.utils

import android.text.method.PasswordTransformationMethod
import android.view.View.OnFocusChangeListener
import android.view.animation.AnimationUtils
import android.widget.EditText
import br.com.luan2.lgutilsk.R
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



fun EditText.isPasswordValid(maxLenght: Int): Boolean {
    return this.textTrim().length > maxLenght
}

fun EditText.checkEmpty(editText: EditText): Boolean {
    return editText.text.trim().length < 0
}

fun EditText.checkError(error: String) {
    this.error = error
    this.isFocusableInTouchMode = true
    this.requestFocus()
    this.shakeView()
}

fun EditText.isEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this.textTrim())
            .matches()
}

fun EditText.isLicensePlate(): Boolean {
    val m = Pattern.compile("[A-Z]{3}\\d{4}").matcher(this.getString().replace("-", "").toUpperCase())

    return m.find()
}

fun EditText.isCep(): Boolean {
    val pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$")
    val matcher = pattern.matcher(this.getString())
    return matcher.find()
}

fun EditText.isValidCep(completion: () -> Unit){
    this.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            if(this.isCep()){
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

fun EditText.addCPFMask() {
//    this.addTextChangedListener(CpfCnpjMask.insert(this,this))
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

