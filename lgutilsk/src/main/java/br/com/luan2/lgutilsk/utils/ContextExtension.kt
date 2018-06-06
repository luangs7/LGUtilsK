package br.com.luan2.lgutilsk.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.graphics.PixelFormat
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.view.Gravity
import android.view.WindowManager
import br.com.luan2.lgutilsk.utils.extras.CustomViewGroup
import org.jetbrains.anko.browse

/**
 * Created by luan silva on 19/04/18.
 */

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


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

@RequiresApi(Build.VERSION_CODES.M)
fun Context.removeStatus(){

        var blockingView: CustomViewGroup?

        val manager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val localLayoutParams = WindowManager.LayoutParams()
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
        localLayoutParams.gravity = Gravity.TOP
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or

                // this is to enable the notification to receive touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or

                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        localLayoutParams.height = (40 * resources.displayMetrics.scaledDensity).toInt()
        localLayoutParams.format = PixelFormat.TRANSPARENT

        blockingView = CustomViewGroup(this)
        manager.addView(blockingView, localLayoutParams)

}

fun Context.setBrightnessTo(brightness:Int){
    val cResolver = this.applicationContext.contentResolver
    Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
}


val Context.isConnectedToNetwork: Boolean
    @SuppressLint("MissingPermission")
    get() = connectivityManager.activeNetworkInfo?.isConnected == true

/**
 * Returns the app version string, or null if it can't be determined.
 */
fun Context.appVersionName(): String? =
        applicationContext.packageManager.getPackageInfo(packageName, 0)?.versionName

/**
 * Returns the app version code, or -1 if it can't be determined.
 */
fun Context.appVersionCode(): Int =
        applicationContext.packageManager.getPackageInfo(packageName, 0)?.versionCode ?: -1

fun Context.copyToClipboard(label: String, text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.primaryClip = clip
}