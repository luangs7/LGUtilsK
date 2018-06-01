package br.com.luan2.lgutilsk.utils

import android.os.Handler
import android.os.Looper
import android.text.Editable
import java.util.concurrent.TimeUnit

/**
 * Created by luan silva on 01/06/18.
 */

private object ContextHandler {
    val handler = Handler(Looper.getMainLooper())
    val mainThread = Looper.getMainLooper().thread
}

fun String.toDecimal():String = String.format("%.2f",this)

fun Int.toDecimal():String = String.format("%.2f",this)

fun Char.decimalValue(): Int {
    if (!isDigit())
        throw IllegalArgumentException("Out of range")
    return this.toInt() - '0'.toInt()
}

fun Int.twoDigitTime() = if (this < 10) "0" + toString() else toString()

fun Editable.replaceAll(newValue: String) {
    replace(0, length, newValue)
}

fun Editable.replaceAllIgnoreFilters(newValue: String) {
    val currentFilters = filters
    filters = emptyArray()
    replaceAll(newValue)
    filters = currentFilters
}

fun isMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()

fun <T : Any> T.TAG() = this::class.simpleName

fun runDelayedOnUiThread(delay: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, action: () -> Unit) {
    ContextHandler.handler.postDelayed(action, timeUnit.toMillis(delay))
}

fun runDelayed(delay: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, action: () -> Unit) {
    Handler().postDelayed(action, timeUnit.toMillis(delay))
}

fun runOnUiThread(action: () -> Unit){
    if (ContextHandler.mainThread == Thread.currentThread()) action() else ContextHandler.handler.post { action() }
}