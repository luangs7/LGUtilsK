package br.com.luan2.lgutilsk.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.util.Log
import java.net.URL
import java.net.URLEncoder
import java.security.MessageDigest

/**
 * Created by luan silva on 19/04/18.
 */
fun String.toast(isShortToast: Boolean = true,context:Context) = toast(this, isShortToast,context)

fun String.md5() = encrypt(this, "MD5")

fun String.sha1() = encrypt(this, "SHA-1")



fun String.equalsIgnoreCase(other: String) = this.toLowerCase().contentEquals(other.toLowerCase())

private fun encrypt(string: String?, type: String): String {
    val bytes = MessageDigest.getInstance(type).digest(string!!.toByteArray())
    return bytes2Hex(bytes)
}

internal fun bytes2Hex(bts: ByteArray): String {
    var des = ""
    var tmp: String
    for (i in bts.indices) {
        tmp = Integer.toHexString(bts[i].toInt() and 0xFF)
        if (tmp.length == 1) {
            des += "0"
        }
        des += tmp
    }
    return des
}

@SuppressLint("NewApi")
fun String.htmlAsSpannable(tagHandler: Html.TagHandler? = null): CharSequence? =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> try {
                Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY,
                        null, tagHandler)
            } catch (e: RuntimeException) {
                // Malformed HTML causes errors
                Log.v("String.kt", "htmlAsSpannable failed", e)
                null
            }
            else -> try {
                @Suppress("DEPRECATION")
                Html.fromHtml(this, null, tagHandler)
            } catch (e: RuntimeException) {
                Log.v("String.kt", "htmlAsSpannable failed", e)
                null
            }
        }

fun String.htmlEncode(): String = TextUtils.htmlEncode(this)


fun String.urlEncode(encoding: String = "UTF-8"): String = URLEncoder.encode(this, encoding)


fun String.toUri(): Uri = Uri.parse(this)


fun String.toUriOrNull(): Uri? =
        try { Uri.parse(this) } catch(e: Exception) { null }


fun String?.compareTo(other: String?) = when {
    this == null && other == null -> 0
    other == null -> -1
    this == null -> 1
    else -> this.compareTo(other)
}

fun String.toURL(context: URL? = null): URL = URL(context, this)

fun String.toURLOrNull(context: URL? = null): URL? = try {
    URL(context, this) } catch(e: Exception) { null }



fun String.convertToCamelCase(): String {
    var titleText = ""
    if (!this.isEmpty()) {
        val words = this.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        words.filterNot { it.isEmpty() }
                .map { it.substring(0, 1).toUpperCase() + it.substring(1).toLowerCase() }
                .forEach { titleText += it + " " }
    }
    return titleText.trim { it <= ' ' }
}

fun String.toBoolean(): Boolean {
    return this != "" &&
            (this.equals("TRUE", ignoreCase = true)
                    || this.equals("Y", ignoreCase = true)
                    || this.equals("YES", ignoreCase = true))
}


fun String.occurrencesOf(ch: Char): Int = this.count { it == ch }
