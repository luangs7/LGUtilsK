package br.com.luan2.lgutilsk.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.support.annotation.AnimRes
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.PopupMenu
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Created by luan silva on 19/04/18.
 */


fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.getBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    draw(canvas)
    canvas.save()
    return bmp
}

fun ListView.setListViewHeightBasedOnChildren() {
    val listAdapter = this.adapter ?: // pre-condition
            return

    var totalHeight = 0
    for (i in 0 until listAdapter.count) {
        val listItem = listAdapter.getView(i, null, this)
        listItem.measure(0, 0)
        totalHeight += listItem.measuredHeight
    }

    val params = this.layoutParams
    params.height = totalHeight + this.dividerHeight * (listAdapter.count - 1)
    this.layoutParams = params
    this.requestLayout()
}

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }

fun <T : View> T.longClick(block: (T) -> Boolean) = setOnLongClickListener { block(it as T) }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int) =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadUrl(url: String) = Picasso.with(context).load(url).into(this)


fun View.addGlobalLayoutListener(l: ViewTreeObserver.OnGlobalLayoutListener) {
    viewTreeObserver?.addOnGlobalLayoutListener(l)
}

fun View.removeGlobalLayoutListener(l: ViewTreeObserver.OnGlobalLayoutListener) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        viewTreeObserver?.removeOnGlobalLayoutListener(l)
    } else {
        viewTreeObserver?.removeGlobalOnLayoutListener(l)
    }
}


fun View.animate(@AnimRes animationResId: Int) {
    startAnimation(AnimationUtils.loadAnimation(context, animationResId))
}


fun View.animate(@AnimRes animationResId: Int,
                 butFirst: Animation.(View) -> Unit = {},
                 andThen: Animation.(View) -> Unit = {},
                 onRepeat: Animation.(View) -> Unit = {}) {
    animate(AnimationUtils.loadAnimation(context, animationResId),
            butFirst, andThen, onRepeat)
}


fun View.animate(animation: Animation,
                 butFirst: Animation.(View) -> Unit = {},
                 andThen: Animation.(View) -> Unit = {},
                 onRepeat: Animation.(View) -> Unit = {}) {
    startAnimation(animation.apply {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation) {
                onRepeat(animation, this@animate)
            }
            override fun onAnimationStart(animation: Animation) {
                butFirst(animation, this@animate)
            }
            override fun onAnimationEnd(animation: Animation) {
                andThen(animation, this@animate)
                animation.setAnimationListener(null)
            }
        })
    })
}


fun Button.disableButton() {
    isEnabled = false
    alpha = 0.3f
}

fun Button.enableButton() {
    isEnabled = true
    alpha = 1.0f
}


/**
 * Finds parent of the view of type [T], not including the view itself.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T> View.findParent(): T? {
    var vp: ViewParent? = parent
    while (vp != null) {
        if (vp is T) return vp
        vp = vp.parent
    }

    return null
}


fun View.showPopup(@MenuRes menuResourceId: Int,
                   onInit: PopupMenu.() -> Unit = {},
                   onClick: (MenuItem) -> Boolean) {
    PopupMenu(context, this).apply {
        menuInflater.inflate(menuResourceId, menu)
        onInit(this)
        setOnMenuItemClickListener(onClick)
    }.show()
}


inline fun View.showIf(condition: () -> Boolean) : View {
    if (visibility != View.VISIBLE && condition()) {
        visibility = View.VISIBLE
    }
    return this
}

inline fun View.hideIf(predicate: () -> Boolean) : View {
    if (visibility != View.INVISIBLE && predicate()) {
        visibility = View.INVISIBLE
    }
    return this
}

fun View.remove() : View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

inline fun View.removeIf(predicate: () -> Boolean) : View {
    if (visibility != View.GONE && predicate()) {
        visibility = View.GONE
    }
    return this
}

fun View.toggleVisibility() : View {
    if (visibility == View.VISIBLE) {
        visibility = View.INVISIBLE
    } else {
        visibility = View.INVISIBLE
    }
    return this
}


//SNACKBAR

/**
 * Default [Snackbar] duration. Set to [Snackbar.LENGTH_LONG].
 */
const val SNACKBAR_DEFAULT_DURATION = Snackbar.LENGTH_LONG

/**
 * Creates a new [Snackbar]
 * with specified [parent] view, [message] and [duration],
 * then calls optional [block] to initialize before displaying.
 *
 * Does nothing if [message] is null.
 *
 * Duration defaults to [SNACKBAR_DEFAULT_DURATION].
 */
fun showSnackbar(parent: View, message: CharSequence?,
                 duration: Int = SNACKBAR_DEFAULT_DURATION,
                 block: Snackbar.() -> Unit = {}) {
    if (message == null) return
    Snackbar.make(parent, message, duration).apply {
        block(this)
    }.show()
}

/**
 * Creates a new [Snackbar]
 * with specified [parent] view, [messageResId] and [duration],
 * then calls optional [block] to initialize before displaying.
 *
 * Duration defaults to [SNACKBAR_DEFAULT_DURATION].
 */
fun showSnackbar(parent: View, @StringRes messageResId: Int,
                 duration: Int = SNACKBAR_DEFAULT_DURATION,
                 block: Snackbar.() -> Unit = {}) {
    Snackbar.make(parent, messageResId, duration).apply {
        block(this)
    }.show()
}

/**
 * Returns the [Snackbar]'s text view by looking for
 * `android.support.design.R.id.snackbar_text` in the view's layout.
 */
val Snackbar.textView: TextView?
    get() = view.findViewById(android.support.design.R.id.snackbar_text) as TextView


