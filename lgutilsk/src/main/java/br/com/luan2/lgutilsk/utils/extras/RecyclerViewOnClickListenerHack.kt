package br.com.luan2.lgutilsk.utils.extras

import android.view.View

/**
 * Created by anderson on 06/06/2015.
 */
interface RecyclerViewOnClickListenerHack {
    fun onClickListener(view: View, position: Int)
    fun onClickListener(view: View, `object`: Any, position: Int)
    //public void onLongPressClickListener(View view, int position);
    // public void onActionClickListener(View view, int position, int action);
}
