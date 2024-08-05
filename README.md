# 📊Android CustomView - Кольцевая секционная диаграмма
## Описание

**Название проекта:** AnalyticalPieChart

AnalyticalPieChart - это кастомная вью, написанная с нуля, без использования каких-либо библиотек, которая отображает данные в виде кольцевой секционной диаграммы, с отображением всех показателей. Данная View можно подстроиться под любое разрешение и любой размер, который вы зададите ей.

С помощью данной View вы сможете сделать лучше и качественней отображение какой-либо статистики с помощью диаграммы в своих android приложения. Улучшить user experience.

Мной было добавлено очень много возможностей для кастомизации View, начиная от изменения толщены диаграммы до изменения отступов текста.
Я надеюсь многим Android разработчиком будет полезно ознакомиться с моей реализацией, ибо в ру сегменте не так уж и много информации о том, как правильно создавать такие View, а также как их кастомизировать под свою задачу.

В самом проекте я продемонстрировал разные примеры и возможности использования данной CustomView, надеюсь многим это понравится😇

*Примеры, которые многие хотят посмотреть*

## **Пример GIF**
![Пример_1](https://github.com/Maksim002/AnalyticalPieChart/blob/main/imgs/example_1.gif)

You can add the View to your layout.

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:background="@color/white"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <com.example.myapplication.customView.AnalyticalPieChart
        android:id="@+id/analyticalPieChart_1"
        android:layout_width="230dp"
        android:layout_height="230dp"
        app:pieChartColors="@array/colors"
        app:pieChartTextAmountColor="@color/purple_700"
        app:pieChartTextDescriptionColor="@color/purple_700"
        app:pieChartTextAmountSize="18sp"
        app:pieChartTextDescriptionSize="16sp"
        app:pieChartTextAmount="Сумма комиссии"
        app:pieChartCircleStrokeWidth="15dp"
        app:pieChartCircleSectionSpace="3"/>
</LinearLayout>
```

Or you can create it programmatically and manually add it to a ViewGroup.

```java
val view1 = findViewById<AnalyticalPieChart>(R.id.analyticalPieChart_1)
view1.setDataChart(  listOf(12, 10, 5, 30, 18), "1000" )
view1.startAnimation()
```
