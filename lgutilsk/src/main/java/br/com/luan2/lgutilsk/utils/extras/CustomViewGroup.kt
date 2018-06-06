package br.com.luan2.lgutilsk.utils.extras

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup

/**
 * Created by Luan on 11/07/17.
 */

class CustomViewGroup(context: Context) : ViewGroup(context) {

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {}

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.v("customViewGroup", "**********Intercepted")
        return true
    }
}