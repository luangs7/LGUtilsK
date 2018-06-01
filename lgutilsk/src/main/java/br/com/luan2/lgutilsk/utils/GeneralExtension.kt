package br.com.luan2.lgutilsk.utils

import android.os.Build
import br.com.luan2.lgutilsk.BuildConfig

/**
 * Created by luan silva on 01/06/18.
 */



/**
 * Wrapping try/catch to ignore catch block
 */
inline fun <T> justTry(block: () -> T) = try { block() } catch (e: Throwable) {}

/**
 * App's debug mode
 */
inline fun debugMode(block : () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}

/**
 * For functionality supported above API 21 (Eg. Material design stuff)
 */
inline fun lollipopAndAbove(block : () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        block()
    }
}

fun isLolliporOrAbove():Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP