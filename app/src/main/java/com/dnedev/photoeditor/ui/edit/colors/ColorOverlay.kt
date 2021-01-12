package com.dnedev.photoeditor.ui.edit.colors

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.dnedev.photoeditor.R

enum class ColorOverlay(
    @ColorInt val photoColorOverlay: Int,
    @ColorRes val colorResource: Int
) {
    BLACK(
        Color.BLACK,
        R.color.black
    ),
    BLUE(
        Color.BLUE,
        R.color.blue
    ),
    CYAN(
        Color.CYAN,
        R.color.cyan
    ),
    GREEN(
        Color.GREEN,
        R.color.green
    ),
    RED(
        Color.RED,
        R.color.red
    ),
    WHITE(
        Color.WHITE,
        R.color.white
    ),
    YELLOW(
        Color.YELLOW,
        R.color.yellow
    ),
    MAGENTA(
        Color.MAGENTA,
        R.color.magenta
    )
}