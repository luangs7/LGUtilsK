package br.com.luan2.lgutilsk.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import org.jetbrains.anko.doAsync
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by luan silva on 19/04/18.
 */
fun Bitmap.getBase64(): String {
    // bitmap = scaleToFitWidth(bitmap,600);// getResizedBitmap(bitmap);
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)

    val byteArray = byteArrayOutputStream.toByteArray()
    val baseImage =Base64.encodeToString(byteArray, Base64.DEFAULT)

    return baseImage
}

fun Bitmap.getBase64(callback:(base64: String) -> Unit) {
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
                out!!.close()
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