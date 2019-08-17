package io.github.vnicius.picpay.ui.common.adapters

import android.view.View

/**
 * Interface to handle the click in the adapter items
 * of the type [T]
 */
interface ItemClick<T> {
    /**
     * Handle the click action
     * @param view clicked
     * @param item of the type [T]
     */
    fun onClick(view: View, item: T)
}