package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.customView.AnalyticalPieChart

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view1 = findViewById<AnalyticalPieChart>(R.id.analyticalPieChart_1)

        view1.setDataChart(
            listOf(12, 10, 5, 30, 18), "1000"
        )
        view1.startAnimation()


        val view2 = findViewById<AnalyticalPieChart>(R.id.analyticalPieChart_2)

        view2.setDataChart(
            listOf(23, 10, 67, 30, 10), "130"
        )
        view2.startAnimation()


        val view3 = findViewById<AnalyticalPieChart>(R.id.analyticalPieChart_3)

        view3.setDataChart(
            listOf(8, 25, 5, 22, 18), "2800"
        )
        view3.startAnimation()


        val view4 = findViewById<AnalyticalPieChart>(R.id.analyticalPieChart_4)

        view4.setDataChart(
            listOf(8, 25, 5, 22, 18), "2800"
        )
        view4.startAnimation()

        val view5 = findViewById<AnalyticalPieChart>(R.id.analyticalPieChart_5)

        view5.setDataChart(
            listOf(8, 25, 5, 22, 18, 12, 10, 5, 30, 18), "2800"
        )
        view5.startAnimation()
    }
}

