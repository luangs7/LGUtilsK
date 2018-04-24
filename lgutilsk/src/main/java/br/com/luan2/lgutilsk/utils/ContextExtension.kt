package br.com.luan2.lgutilsk.utils

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.net.Uri
import org.jetbrains.anko.browse

/**
 * Created by luan silva on 19/04/18.
 */


fun Context.makeCall(number: String): Boolean {
    try {
        val intent = Intent(ACTION_CALL, Uri.parse("tel:$number"))
        startActivity(intent)
        return true
    } catch (e: Exception) {
        return false
    }
}

fun Context.sms(phone: String?, body: String = "") {
    val smsToUri = Uri.parse("smsto:" + phone)
    val intent = Intent(Intent.ACTION_SENDTO, smsToUri)
    intent.putExtra("sms_body", body)
    startActivity(intent)
}

fun Context.rate(): Boolean = browse("market://details?id=$packageName") or browse("http://play.google.com/store/apps/details?id=$packageName")
