package br.com.luan2.lgutilsk.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
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
