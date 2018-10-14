package br.com.luan2.lgutilsk.utils

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.support.annotation.LayoutRes
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import br.com.luan2.lgutilsk.BuildConfig
import br.com.luan2.lgutilsk.extras.mask.MyMaskEditText
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * Created by luan silva on 01/06/18.
 */

/**
 * Wrapping try/catch to ignore catch block
 */
inline fun <T> justTry(block: () -> T) = try {
    block()
} catch (e: Throwable) {
}

/**
 * App's debug mode
 */
inline fun debugMode(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}

/**
 * For functionality supported above API 21 (Eg. Material design stuff)
 */
inline fun lollipopAndAbove(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        block()
    }
}

inline fun aboveApi(api: Int, included: Boolean = false, block: () -> Unit) {
    if (Build.VERSION.SDK_INT > if (included) api - 1 else api) {
        block()
    }
}

inline fun belowApi(api: Int, included: Boolean = false, block: () -> Unit) {
    if (Build.VERSION.SDK_INT < if (included) api + 1 else api) {
        block()
    }
}

fun isLolliporOrAbove(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

infix fun <T> Boolean.then(param: T): T? = if (this) param else null

infix fun <T> Boolean.yes(trueValue: () -> T) = TernaryOperator(trueValue, this)

class TernaryOperator<out T>(val trueValue: () -> T, val bool: Boolean)

infix inline fun <T> TernaryOperator<T>.no(falseValue: () -> T) = if (bool) trueValue() else falseValue()

fun inflate(@LayoutRes layoutId: Int, parent: ViewGroup?, attachToRoot: Boolean = false) = LayoutInflater.from(app).inflate(layoutId, parent, attachToRoot)!!

fun inflate(@LayoutRes layoutId: Int) = inflate(layoutId, null)

fun delay(delay: Long, f: () -> Unit) {
    Handler().postDelayed(f, delay)
}

fun <T> lazyMain(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

inline infix fun <reified T> T?.guard(call: () -> Unit): T? {
    if (this is T) return this
    else {
        call()
        return null
    }
}

//    println(condition then "true" ?: "false")

fun Boolean.stringValue(): String = this then "1" ?: "0"

fun Any.debug(message: String) {
    Log.d(this::class.java.simpleName, message)
}

fun Any.debug(message: String, tr: Throwable) {
    Log.d(this::class.java.simpleName, message, tr)
}

fun Any.error(message: String) {
    Log.e(this::class.java.simpleName, message)
}

fun Any.error(message: String, tr: Throwable) {
    Log.e(this::class.java.simpleName, message, tr)
}

fun Any.info(message: String) {
    Log.i(this::class.java.simpleName, message)
}

fun Any.info(message: String, tr: Throwable) {
    Log.i(this::class.java.simpleName, message, tr)
}

fun Any.verbose(message: String) {
    Log.v(this::class.java.simpleName, message)
}

fun Any.verbose(message: String, tr: Throwable) {
    Log.v(this::class.java.simpleName, message, tr)
}

fun Any.warn(message: String) {
    Log.w(this::class.java.simpleName, message)
}

fun Any.warn(message: String, tr: Throwable) {
    Log.w(this::class.java.simpleName, message, tr)
}

fun Any.warn(tr: Throwable) {
    Log.w(this::class.java.simpleName, tr)
}

fun Any.wtf(message: String) {
    Log.wtf(this::class.java.simpleName, message)
}

fun Any.wtf(message: String, tr: Throwable) {
    Log.wtf(this::class.java.simpleName, message, tr)
}

fun Any.wtf(tr: Throwable) {
    Log.wtf(this::class.java.simpleName, tr)
}


fun Any.toJson(): RequestBody =
        RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Gson().toJson(this))

fun JSONObject.toJson(): RequestBody =
        RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Gson().toJson(this))

//fun JSONObject.toJson(): RequestBody =
//        RequestBody.create(MediaType.parse("application/json; charset=utf-8"), Gson().toJson(this))

val Activity.hasNavigationBar: Boolean
    get() {
        if (Build.VERSION.SDK_INT < 19) return false
        return !ViewConfiguration.get(this).hasPermanentMenuKey()
    }

fun Activity.isPortrait() = resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE
fun Activity.isLandscape() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

/**
 * Return true if navigation bar is at the bottom, false otherwise
 */
val Activity.isNavigationBarHorizontal: Boolean
    get() {
        if (!hasNavigationBar) return false
        val dm = resources.displayMetrics
        return !navigationBarCanChangeItsPosition || dm.widthPixels < dm.heightPixels
    }

/**
 * Return true if navigation bar change its position when device rotates, false otherwise
 */
val Activity.navigationBarCanChangeItsPosition: Boolean // Only phone between 0-599dp can
    get() {
        val dm = resources.displayMetrics
        return dm.widthPixels != dm.heightPixels && resources.configuration.smallestScreenWidthDp < 600
    }

val Activity.navigationBarHeight: Int
    get() {
        if (!hasNavigationBar) return 0
        if (navigationBarCanChangeItsPosition && !isPortrait()) return 0
        val idString = if (isPortrait()) "navigation_bar_height" else "navigation_bar_height_landscape"
        val id = resources.getIdentifier(idString, "dimen", "android")
        if (id > 0) return resources.getDimensionPixelSize(id)
        return 0
    }

fun Activity.hasNavBar(resources: Resources): Boolean {
    val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    return id > 0 && resources.getBoolean(id)
}

fun EditText.myCustomMask(mask: String) {
    addTextChangedListener(MyMaskEditText(this, mask))
}

fun Activity.sendNotification(messageBody: String?, title: String? = this.appVersionName(), pendingIntentActivity: Activity) {

    val intent = Intent(this, pendingIntentActivity::class.java)


    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)

    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setColor(resources.getColor(android.R.color.black))
            .setContentTitle(title)
            .setContentText(messageBody)
            .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setOngoing(false)

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
}

fun ImageView.loadUrlAndGetBitmap(url: String, completion: (Bitmap?) -> Unit) {

    Picasso.with(context).load(url).into(object : Target {
        override fun onBitmapFailed(errorDrawable: Drawable?) {
            completion(null)
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            bitmap?.let {
                this@loadUrlAndGetBitmap.setImageBitmap(it)
                completion(it)
            } ?: completion(null)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        }
    })
}

// Method to save an bitmap to a file
fun Bitmap.bitmapToFile(context: Context): Uri {
    // Get the context wrapper
    val wrapper = ContextWrapper(context)

    // Initialize a new file instance to save bitmap object
    var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
    file = File(file, "donwload-image.jpg")

    try {
        // Compress the bitmap and save in jpg format
        val stream: OutputStream = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    // Return the saved bitmap uri
    return Uri.parse(file.absolutePath)
}
