package br.com.luan2.lgutilsk.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import br.com.luan2.lgutilsk.BuildConfig
import br.com.luan2.lgutilsk.R
import org.jetbrains.anko.longToast
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.share
import java.lang.Exception
import java.text.SimpleDateFormat

/**
 * Created by luan gabriel on 16/04/18.
 */

fun Activity.hideKeyboard() {
    try {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    } catch (e: Exception) {
        // Ignore exceptions if any
        Log.e("hideKeyboard", e.toString(), e)
    }

}

fun Activity.showKeyboard() {
    try {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(this.currentFocus, InputMethodManager.SHOW_FORCED)
    } catch (e: Exception) {
        // Ignore exceptions if any
        Log.e("hideKeyboard", e.toString(), e)
    }

}

fun Activity.showDialog(title: String, message: String) {
    try {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(title).setMessage(message)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        builder.create().show()
    } catch (e: Exception) {
        // Ignore exceptions if any
        Log.e("showDialog", e.toString(), e)
    }

}

fun Activity.showDialog(title: String, message: String, callback: (dialog: DialogInterface) -> Unit) {
    try {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(title).setMessage(message)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    if (callback != null)
                        callback(dialog)
                    else
                        dialog.dismiss()
                })
        builder.create().show()
    } catch (e: Exception) {
        // Ignore exceptions if any
        Log.e("showDialog", e.toString(), e)
    }

}

fun Activity.showDialog(title: String, message: String, positive: String, negative: String, callback: (positive: Boolean, negative: Boolean, dialog: DialogInterface) -> Unit) {
    try {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(title).setMessage(message)
                .setPositiveButton(positive, DialogInterface.OnClickListener { dialog, id ->
                    if (callback != null)
                        callback(true, false, dialog)
                    else
                        dialog.dismiss()
                })
                .setNegativeButton(negative, DialogInterface.OnClickListener { dialog, id ->
                    if (callback != null)
                        callback(false, true, dialog)
                    else
                        dialog.dismiss()
                })
        builder.create().show()
    } catch (e: Exception) {
        // Ignore exceptions if any
        Log.e("showDialog", e.toString(), e)
    }
}

fun Activity.showDialog(messange: String) = showDialog("", messange)

fun Activity.openNavigation(latitude: String, longitude: String) {
    try {
        val uri = "google.navigation:q=$latitude,$longitude"
        startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse(uri)).setPackage("com.google.android.apps.maps"))
    } catch (ex: Exception) {

        try {
            val uri = "geo:$latitude,$longitude"
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(uri)))
        } catch (e2: Exception) {
            Toast.makeText(this, "Não é possivel abrir o Google Maps.", Toast.LENGTH_SHORT).show()
        }

    }
}

inline fun Activity.openGoogleMaps(latitude: String, longitude: String) = openGoogleMaps(latitude, longitude, "")


fun Activity.openGoogleMaps(latitude: String, longitude: String, query: String) {

    try {
        if (query.isEmpty()) {
            val uri = "geo:$latitude,$longitude"
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(uri)).setPackage("com.google.android.apps.maps"))
        } else {
            val uri = "geo:$latitude,$longitude?q=" + Uri.encode(query)

            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse(uri)).setPackage("com.google.android.apps.maps"))
        }

    } catch (ex: Exception) {

        openMaps(latitude, longitude)

    }
}

fun Activity.openWaze(latitude: String, longitude: String) {

    try {
        val uri = "waze://?ll=$latitude,$longitude"
        startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse(uri)))

    } catch (ex: Exception) {

        openMaps(latitude, longitude)

    }
}

fun Activity.openMaps(latitude: String, longitude: String) {
    try {
        val uri = "geo:$latitude,$longitude"
        startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse(uri)))
    } catch (e2: Exception) {
        Toast.makeText(this, "Não é possivel abrir o Waze.", Toast.LENGTH_SHORT).show()
    }
}

fun Activity.open(cls: Class<*>) {
    startActivity(Intent(this, cls))
}

fun Activity.startActivityClearTask(activity: Activity) {
    val intent = Intent(baseContext, activity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    finish()
    startActivity(intent)

}

fun Activity.clearWindowBackground() = window.setBackgroundDrawable(null)

fun Activity.steepStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }
}

fun Activity.showDebugDBAddressLogToast() {
    if (BuildConfig.DEBUG) {
        try {
            val debugDB = Class.forName("com.amitshekhar.DebugDB")
            val getAddressLog = debugDB.getMethod("getAddressLog")
            val value = getAddressLog.invoke(null)
            Log.d("DB_DEBUG", value as String)
            //                Toast.makeText(context, (String) value, Toast.LENGTH_LONG).show();
        } catch (ignore: Exception) {

        }

    }
}

fun Activity.onSharedButton(text: String, subject: String) {
    share(text, subject)
}

fun Activity.onPhoneDispatcher(number: String) {
    makeCall(number)
}

fun Activity.onAlertDialogMessage(title: String, text: String) {
    val builder = android.support.v7.app.AlertDialog.Builder(this, R.style.AlertDialog_AppCompat)
    builder.setTitle(title)
    builder.setMessage(text)
    builder.setCancelable(false)
    builder.setPositiveButton("OK") { arg0, _ ->
        arg0.dismiss()
        finish()
    }

    val alerta = builder.create()
    alerta.show()
}


fun Activity.onAlertDialogMessageFinish(title: String, text: String, mActivity: Activity) {
    val builder = android.support.v7.app.AlertDialog.Builder(this, R.style.AlertDialog_AppCompat)
    builder.setTitle(title)
    builder.setCancelable(false)
    builder.setMessage(text)
    builder.setPositiveButton("OK") { arg0, _ ->
        arg0.dismiss()
        finishAffinity()
        startActivity(Intent(baseContext, mActivity.javaClass))
    }

    val alerta = builder.create()
    alerta.show()
}

fun Activity.onErrorAlert(erro: String) {
    longToast(erro)
}

fun Activity.onAlertMessage(msg: String) {
    longToast(msg)

}

fun Activity.currentTimeMonthAndYear(): String {
    val date = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("MMMM yyyy")

    return dateFormat.format(date)
}

fun Activity.getCurrentTimeFormatted(format: String): String {
    val date = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat(format)

    return dateFormat.format(date)
}

fun Activity.startActivity(activity: Activity){
    startActivity(Intent(this, activity.javaClass))
}


