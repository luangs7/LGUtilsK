package br.com.luan2.lgutilsk.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi
import android.util.Base64
import android.widget.ImageView
import org.jetbrains.anko.doAsync
import java.io.*

/**
 * Created by luan silva on 19/04/18.
 */
fun Bitmap.getBase64(): String {
    // bitmap = scaleToFitWidth(bitmap,600);// getResizedBitmap(bitmap);
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)

    val byteArray = byteArrayOutputStream.toByteArray()
    val baseImage = Base64.encodeToString(byteArray, Base64.DEFAULT)

    return baseImage
}

fun Bitmap.getBase64(callback: (base64: String) -> Unit) {
    doAsync {
        val base = getBase64()
        callback(base)
    }
}


// Scale and maintain aspect ratio given a desired width
// BitmapScaler.scaleToFitWidth(bitmap, 100);
fun Bitmap.scaleToFitWidth(width: Int): Bitmap {
    val factor = width / this.width.toFloat()
    return if (this.width > width) {
        Bitmap.createScaledBitmap(this, width, (this.height * factor).toInt(), true)
    } else {
        Bitmap.createBitmap(this)
    }

}


// Scale and maintain aspect ratio given a desired height
// BitmapScaler.scaleToFitHeight(bitmap, 100);
fun Bitmap.scaleToFitHeight(height: Int): Bitmap {
    val factor = height / this.height.toFloat()
    return if (this.height > height) {
        Bitmap.createScaledBitmap(this, (this.width * factor).toInt(), height, true)
    } else {
        Bitmap.createBitmap(this)
    }
}


fun Bitmap.saveBitmap(filePath: String, imageType: String, compression: Int) {

    var out: FileOutputStream? = null
    try {
        out = FileOutputStream(filePath)
        // PNG is a loss less format, the compression factor (100) is ignored
        if (imageType == "png" || imageType == "PNG" || imageType == ".png") {
            this.compress(Bitmap.CompressFormat.PNG, compression, out)
        } else if (imageType == "jpg" || imageType == "JPG" || imageType == ".jpg") {
            this.compress(Bitmap.CompressFormat.JPEG, compression, out)
        } else if (imageType == "jpeg" || imageType == "JPEG" || imageType == ".jpeg") {
            this.compress(Bitmap.CompressFormat.JPEG, compression, out)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            if (out != null) {
                out.close()
            }
        } catch (e: IOException) {
        }

    }
}

fun Bitmap.getImageUri(inContext: Context): Uri {
    val bytes = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, this, "Title", null)
    return Uri.parse(path)
}

fun Bitmap.decodeFile(file: File, requiredHeight: Int): Bitmap? {
    try {
        // Decode image size
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        BitmapFactory.decodeStream(FileInputStream(file), null, o)

        // Find the correct scale value. It should be the power of 2.
        var scale = 1
        while (o.outWidth / scale / 2 >= requiredHeight && o.outHeight / scale / 2 >= requiredHeight) {
            scale *= 2
        }
        // Decode with inSampleSize
        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        return BitmapFactory.decodeStream(FileInputStream(file), null, o2)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return null
    }

}

fun Bitmap.fixOrientation(): Bitmap {
    var mBitmap = this
    if (mBitmap.width > mBitmap.height) {
        val matrix = Matrix()
        matrix.postRotate(90f)
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.width, mBitmap.height, matrix, true)
    }
    return mBitmap
}


infix fun Bitmap.rotate(degree: Int): Bitmap {
    val w = width
    val h = height

    val mtx = Matrix()
    mtx.postRotate(degree.toFloat())

    return Bitmap.createBitmap(this, 0, 0, w, h, mtx, true)
}

fun Bitmap.toUriJpeg(context: Context, title: String, description: String): Uri {
    val bytes = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, this, title, description)
    return Uri.parse(path)
}

fun Bitmap.toUriPng(context: Context, title: String, description: String): Uri {
    val bytes = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, this, title, description)
    return Uri.parse(path)
}

fun Bitmap.toUriWebp(context: Context, title: String, description: String): Uri {
    val bytes = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.WEBP, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, this, title, description)
    return Uri.parse(path)
}

fun Bitmap.makeCircle(): Bitmap? {
    val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)

    val color = -0xbdbdbe
    val paint = Paint()
    val rect = Rect(0, 0, width, height)

    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color

    canvas.drawCircle(width.div(2f), height.div(2f), width.div(2f), paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)

    return output
}

fun Bitmap.toDrawable(context: Context): Drawable {
    return BitmapDrawable(context.resources, this)
}

fun Drawable.toBitmap(): Bitmap? {
    val bitmap: Bitmap? = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    } else {
        Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    }

    if (this is BitmapDrawable) {
        if (this.bitmap != null) {
            return this.bitmap
        }
    }

    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)

    return bitmap
}

@Throws(FileNotFoundException::class)
fun Uri.toBitmap(context: Context): Bitmap {
    return MediaStore.Images.Media.getBitmap(context.contentResolver, this)
}

@Throws(FileNotFoundException::class)
fun Uri.toDrawable(context: Context): Drawable {
    val inputStream = context.contentResolver.openInputStream(this)
    return Drawable.createFromStream(inputStream, this.toString())
}

infix fun ImageView.set(@DrawableRes id: Int) =
        setImageResource(id)


infix fun ImageView.set(bitmap: Bitmap) =
        setImageBitmap(bitmap)


infix fun ImageView.set(drawable: Drawable) =
        setImageDrawable(drawable)


@RequiresApi(Build.VERSION_CODES.M)
infix fun ImageView.set(ic: Icon) =
        setImageIcon(ic)


infix fun ImageView.set(uri: Uri) =
        setImageURI(uri)
