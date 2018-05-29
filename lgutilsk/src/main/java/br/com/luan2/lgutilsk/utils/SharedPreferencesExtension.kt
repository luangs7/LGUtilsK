package br.com.luan2.lgutilsk.utils

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by luan silva on 19/04/18.
 */

inline val app: Application
    get() = Ext.ctx

private inline val sp: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(app)

fun spSetInt(key: String, value: Int) = sp.edit().putInt(key, value).apply()

fun spGetInt(key: String, defaultValue: Int = 0) = sp.getInt(key, defaultValue)

fun spSetLong(key: String, value: Long) = sp.edit().putLong(key, value).apply()

fun spGetLong(key: String, defaultValue: Long = 0L) = sp.getLong(key, defaultValue)

fun spSetFloat(key: String, value: Float) = sp.edit().putFloat(key, value).apply()

fun spGetFloat(key: String, defaultValue: Float = 0f) = sp.getFloat(key, defaultValue)

fun spSetBoolean(key: String, value: Boolean) = sp.edit().putBoolean(key, value).apply()

fun spGetBoolean(key: String, defaultValue: Boolean = false) = sp.getBoolean(key, defaultValue)

fun spSetString(key: String, value: String) = sp.edit().putString(key, value).apply()

fun spGetString(key: String, defaultValue: String = "") = sp.getString(key, defaultValue)!!

fun spRemove(key: String) = sp.edit().remove(key).apply()

fun spClearAll() = sp.edit().clear().apply()


object Ext {
    lateinit var ctx: Application

    fun with(app: Application) {
        this.ctx = app
    }
}