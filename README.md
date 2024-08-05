# 📊Android CustomView - Кольцевая секционная диаграмма
## Описание

**Название проекта:** AnalyticalPieChart

AnalyticalPieChart - это кастомная вью, написанная с нуля, без использования каких-либо библиотек, которая отображает данные в виде кольцевой секционной диаграммы, с отображением всех показателей. Данная View можно подстроиться под любое разрешение и любой размер, который вы зададите ей.

С помощью данной View вы сможете сделать лучше и качественней отображение какой-либо статистики с помощью диаграммы в своих android приложения. Улучшить user experience.

Мной было добавлено очень много возможностей для кастомизации View, начиная от изменения толщены диаграммы до изменения отступов текста.
Я надеюсь многим Android разработчиком будет полезно ознакомиться с моей реализацией, ибо в ру сегменте не так уж и много информации о том, как правильно создавать такие View, а также как их кастомизировать под свою задачу.

В самом проекте я продемонстрировал разные примеры и возможности использования данной CustomView, надеюсь многим это понравится😇

*Примеры, которые многие хотят посмотреть*
## **Пример**
![Пример_1](https://github.com/Alex-tech-it/CustomView_AnalyticalPieChart/raw/master/imgs/exemle_1.jpg)

## **Пример GIF**
![Пример_1](https://github.com/Alex-tech-it/CustomView_AnalyticalPieChart/raw/master/imgs/exemle_1.gif)

<com.example.myapplication.customView.AnalyticalPieChart
android:id="@+id/analyticalPieChart_2"
android:layout_width="150dp"
android:layout_height="150dp"
app:pieChartColors="@array/colors"
app:pieChartTextAmountColor="@color/teal_200"
app:pieChartTextDescriptionColor="@color/teal_200"
app:pieChartTextAmountSize="16sp"
app:pieChartTextDescriptionSize="14sp"
app:pieChartTextAmount="Сумма камисии"
app:pieChartCircleStrokeWidth="5dp"
app:pieChartCircleSectionSpace="2"/>