package com.example.myapplication.model

/**
 * @author Alex-tech-it
 * Github - https://github.com/Alex-tech-it
 */


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