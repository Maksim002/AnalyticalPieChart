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

//        val view = findViewById<DoughnutChartView>(R.id.doughnutChart)
//        view.setPercentages(
//            mapOf(
//                R.color.purple_200 to 25.0,
//                R.color.purple_700 to 25.0,
//                R.color.teal_200 to 25.0,
//                R.color.black to 25.0
//            )
//        )

        val view = findViewById<AnalyticalPieChart>(R.id.analyticalPieChart_1)

        view.setDataChart(
            listOf(12, 10, 5), "1000"
        )
        view.startAnimation()
    }
}

