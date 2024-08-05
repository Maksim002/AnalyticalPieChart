package com.example.myapplication.model

import android.os.Parcelable
import android.view.View.BaseSavedState

/**
 * Собственный state для сохранения и восстановление данных
 */
class AnalyticalPieChartState(
    private val superSavedState: Parcelable?,
    val dataList: List<Int>
) : BaseSavedState(superSavedState), Parcelable {
}