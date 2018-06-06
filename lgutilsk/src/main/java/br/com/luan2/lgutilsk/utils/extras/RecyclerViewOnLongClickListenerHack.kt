package br.com.squarebits.ninky.extras

import android.view.View

/**
 * Created by anderson on 06/06/2015.
 */
interface RecyclerViewOnLongClickListenerHack {
    fun onLongPressClickListener(view: View, position: Int)
    fun onLongPressClickListener(view: View, `object`: Any, position: Int)
    // public void onActionClickListener(View view, int position, int action);
}
