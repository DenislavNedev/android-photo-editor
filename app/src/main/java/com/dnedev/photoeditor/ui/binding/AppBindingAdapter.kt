package com.dnedev.photoeditor.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter

object AppBindingAdapter {

    @BindingAdapter("visibleGone")
    fun visibleGone(
        view: View,
        isVisible: Boolean
    ) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}