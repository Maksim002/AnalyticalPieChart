package com.example.myapplication.extension

import android.content.Context
import android.util.TypedValue

/**
 * Context Extension для конвертирования значения в пиксели.
 * @property dp - значение density-independent pixels
 */
fun Context.dpToPx(dp: Int): Float {
    return dp.toFloat() * this.resources.displayMetrics.density
}

/**
 * Context Extension для конвертирования значения размера шрифта в пиксели.
 * @property sp - значение scale-independent pixels
 */
fun Context.spToPx(sp: Int): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), this.resources.displayMetrics);
}