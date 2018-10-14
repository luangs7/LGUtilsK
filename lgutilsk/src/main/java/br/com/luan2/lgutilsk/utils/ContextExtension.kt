package br.com.luan2.lgutilsk.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.FontRes
import android.support.annotation.IntegerRes
import android.support.annotation.RequiresApi
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            startActivity(intent)
            return true
        }
        return false
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
fun Context.removeStatus() {

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

fun Context.setBrightnessTo(brightness: Int) {
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

fun Context.onToast(message: String, completion: () -> Unit) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    Handler().postDelayed({
        completion()
    }, 3500)
}

fun Context.color(@ColorRes clr: Int): Int = ContextCompat.getColor(this, clr)

fun Context.string(@StringRes str: Int): String = getString(str)

fun Context.drawable(@DrawableRes drw: Int): Drawable? = ContextCompat.getDrawable(this, drw)

fun Context.dimen(@DimenRes dmn: Int): Float = resources.getDimension(dmn)

fun Context.dimenInt(@DimenRes dmn: Int): Int = resources.getDimensionPixelSize(dmn)

fun Context.int(@IntegerRes int: Int): Int = resources.getInteger(int)

fun Context.font(@FontRes font: Int): Typeface? = ResourcesCompat.getFont(this, font)

fun Context.stringArray(array: Int): Array<String> = resources.getStringArray(array)

fun Context.intArray(array: Int): IntArray = resources.getIntArray(array)

fun Context.toast(message: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()
