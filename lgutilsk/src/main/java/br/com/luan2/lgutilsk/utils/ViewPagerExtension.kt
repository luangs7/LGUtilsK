package br.com.luan2.lgutilsk.utils

import android.support.v4.view.ViewPager

/**
 * Created by luan silva on 06/06/18.
 */


 fun ViewPager.back(animate: Boolean = true) = setCurrentItem(currentItem - 1, animate)

 fun ViewPager.forward(animate: Boolean = true) = setCurrentItem(currentItem + 1, animate)

 fun ViewPager.isOnLastPage(): Boolean = currentItem == (adapter?.count ?: 0) - 1

 fun ViewPager.isOnFirstPage(): Boolean = currentItem == 0
