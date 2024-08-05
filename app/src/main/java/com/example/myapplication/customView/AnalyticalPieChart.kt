package com.example.myapplication.customView

/**
 * @author Alex-tech-it
 * Github - https://github.com/Alex-tech-it
 */

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Parcelable
import android.text.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.myapplication.R
import com.example.myapplication.extension.dpToPx
import com.example.myapplication.extension.spToPx
import com.example.myapplication.model.AnalyticalPieChartModel
import com.example.myapplication.model.AnalyticalPieChartState

class AnalyticalPieChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), AnalyticalPieChartInterface {

    /**
     * Базовые значения для полей и самой [AnalyticalPieChart]
     */
    companion object {
        private const val DEFAULT_MARGIN_TEXT_1 = 2
        private const val DEFAULT_MARGIN_TEXT_2 = 10
        private const val DEFAULT_MARGIN_TEXT_3 = 2
        private const val DEFAULT_MARGIN_SMALL_CIRCLE = 12

        /* Процент ширины для отображения круговой диаграммы от общей ширины View */
        private const val CIRCLE_WIDTH_PERCENT = 0.86

        /* Базовые значения ширины и высоты View */
        const val DEFAULT_VIEW_SIZE_HEIGHT = 150
        const val DEFAULT_VIEW_SIZE_WIDTH = 250
    }

    private var marginTextFirst: Float = context.dpToPx(DEFAULT_MARGIN_TEXT_1)
    private var marginTextSecond: Float = context.dpToPx(DEFAULT_MARGIN_TEXT_2)
    private var marginTextThird: Float = context.dpToPx(DEFAULT_MARGIN_TEXT_3)
    private var marginSmallCircle: Float = context.dpToPx(DEFAULT_MARGIN_SMALL_CIRCLE)
    private val marginText: Float = marginTextFirst + marginTextSecond
    private val circleRect = RectF()
    private var circleStrokeWidth: Float = context.dpToPx(6)
    private var circleRadius: Float = 0F
    private var circlePadding: Float = context.dpToPx(8)
    private var circlePaintRoundSize: Boolean = true
    private var circleSectionSpace: Float = 3F
    private var circleCenterX: Float = 0F
    private var circleCenterY: Float = 0F
    private var descriptionTextPain: TextPaint = TextPaint()
    private var amountTextPaint: TextPaint = TextPaint()
    private var textStartX: Float = 0F
    private var textStartY: Float = 0F
    private var textHeight: Int = 0
    private var textCircleRadius: Float = context.dpToPx(4)
    private var textAmountStr: String = ""
    private var textAmountY: Float = 0F
    private var textAmountXNumber: Float = 0F
    private var textAmountXDescription: Float = 0F
    private var textAmountYDescription: Float = 0F
    private var totalAmount: Int = 0
    private var pieChartColors: List<String> = listOf()
    private var percentageCircleList: List<AnalyticalPieChartModel> = listOf()
    private var textRowList: MutableList<StaticLayout> = mutableListOf()
    private var dataList: List<Int> = listOf()
    private var animationSweepAngle: Int = 0

    /**
     * В INIT блоке инициализируются все необходимые поля и переменные.
     * Необходимые значения вытаскиваются из специальных Attr тегов
     * (<declare-styleable name="AnalyticalPieChart">).
     */
    init {
        // Задаем базовые значения и конвертируем в px
        var textAmountSize: Float = context.spToPx(22)
        var textDescriptionSize: Float = context.spToPx(14)
        var textAmountColor: Int = Color.WHITE
        var textDescriptionColor: Int = Color.GRAY

        // Инициализурем поля View, если Attr присутствуют
        if (attrs != null) {
            val typeArray = context.obtainStyledAttributes(attrs, R.styleable.AnalyticalPieChart)

            // Секция списка цветов
            val colorResId =
                typeArray.getResourceId(R.styleable.AnalyticalPieChart_pieChartColors, 0)
            pieChartColors = typeArray.resources.getStringArray(colorResId).toList()

            // Секция отступов
            marginTextFirst = typeArray.getDimension(
                R.styleable.AnalyticalPieChart_pieChartMarginTextFirst,
                marginTextFirst
            )
            marginTextSecond = typeArray.getDimension(
                R.styleable.AnalyticalPieChart_pieChartMarginTextSecond,
                marginTextSecond
            )
            marginTextThird = typeArray.getDimension(
                R.styleable.AnalyticalPieChart_pieChartMarginTextThird,
                marginTextThird
            )
            marginSmallCircle = typeArray.getDimension(
                R.styleable.AnalyticalPieChart_pieChartMarginSmallCircle,
                marginSmallCircle
            )

            // Секция круговой диаграммы
            circleStrokeWidth = typeArray.getDimension(
                R.styleable.AnalyticalPieChart_pieChartCircleStrokeWidth,
                circleStrokeWidth
            )
            circlePadding = typeArray.getDimension(
                R.styleable.AnalyticalPieChart_pieChartCirclePadding,
                circlePadding
            )
            circlePaintRoundSize = typeArray.getBoolean(
                R.styleable.AnalyticalPieChart_pieChartCirclePaintRoundSize,
                circlePaintRoundSize
            )
            circleSectionSpace = typeArray.getFloat(
                R.styleable.AnalyticalPieChart_pieChartCircleSectionSpace,
                circleSectionSpace
            )

            // Секция текста
            textCircleRadius = typeArray.getDimension(
                R.styleable.AnalyticalPieChart_pieChartTextCircleRadius,
                textCircleRadius
            )
            textAmountSize = typeArray.getDimension(
                R.styleable.AnalyticalPieChart_pieChartTextAmountSize,
                textAmountSize
            )
            textAmountColor = typeArray.getColor(
                R.styleable.AnalyticalPieChart_pieChartTextAmountColor,
                textAmountColor
            )
            textDescriptionSize = typeArray.getDimension(
                R.styleable.AnalyticalPieChart_pieChartTextDescriptionSize,
                textDescriptionSize
            )
            textDescriptionColor = typeArray.getColor(
                R.styleable.AnalyticalPieChart_pieChartTextDescriptionColor,
                textDescriptionColor
            )
            textAmountStr =
                typeArray.getString(R.styleable.AnalyticalPieChart_pieChartTextAmount) ?: ""

            typeArray.recycle()
        }

        circlePadding += circleStrokeWidth

        // Инициализация кистей View
        initPains(amountTextPaint, textAmountSize, textAmountColor)
        initPains(descriptionTextPain, textDescriptionSize, textDescriptionColor, true)
    }

    /**
     * Метод жизненного цикла View.
     * Расчет необходимой ширины и высоты View.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        textRowList.clear()

        val initSizeWidth = resolveDefaultSize(widthMeasureSpec, DEFAULT_VIEW_SIZE_WIDTH)
        val initSizeHeight = calculateViewHeight(heightMeasureSpec)

        textStartX = initSizeWidth - initSizeWidth.toFloat()
        textStartY = initSizeHeight.toFloat() / 2 - textHeight / 2

        calculateCircleRadius(initSizeWidth, initSizeHeight)

        setMeasuredDimension(initSizeWidth, initSizeHeight)
    }

    /**
     * Метод жизненного цикла View.
     * Отрисовка всех необходимых компонентов на Canvas.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawCircle(canvas)
        drawText(canvas)
    }

    /**
     * Восстановление данных из [AnalyticalPieChartState]
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        val analyticalPieChartState = state as? AnalyticalPieChartState
        super.onRestoreInstanceState(analyticalPieChartState?.superState ?: state)

        dataList = analyticalPieChartState?.dataList ?: listOf()
    }

    /**
     * Сохранение [dataList] в собственный [AnalyticalPieChartState]
     */
    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return AnalyticalPieChartState(superState, dataList)
    }

    /**
     * Имплиментируемый метод интерфейса взаимодействия [AnalyticalPieChartInterface].
     * Добавление данных в View.
     */
    override fun setDataChart(list: List<Int>, sumValue: String) {
        totalAmount = sumValue.toInt()
        dataList = list
        calculatePercentageOfData()
    }

    /**
     * Имплиментируемый метод интерфейса взаимодействия [AnalyticalPieChartInterface].
     * Запуск анимации отрисовки View.
     */
    override fun startAnimation() {
        // Проход значений от 0 до 360 (целый круг), с длительностью - 1.5 секунды
        val animator = ValueAnimator.ofInt(0, 360).apply {
            duration = 1500
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener { valueAnimator ->
                animationSweepAngle = valueAnimator.animatedValue as Int
                invalidate()
            }
        }
        animator.start()
    }

    /**
     * Метод отрисовки круговой диаграммы на Canvas.
     */
    private fun drawCircle(canvas: Canvas) {
        for (percent in percentageCircleList) {
            if (animationSweepAngle > percent.percentToStartAt + percent.percentOfCircle) {
                canvas.drawArc(
                    circleRect,
                    percent.percentToStartAt,
                    percent.percentOfCircle,
                    false,
                    percent.paint
                )
            } else if (animationSweepAngle > percent.percentToStartAt) {
                canvas.drawArc(
                    circleRect,
                    percent.percentToStartAt,
                    animationSweepAngle - percent.percentToStartAt,
                    false,
                    percent.paint
                )
            }
        }
    }

    /**
     * Метод отрисовки всего текста диаграммы на Canvas.
     */
    private fun drawText(canvas: Canvas) {
        canvas.drawText(
            amountForm(totalAmount),
            textAmountXNumber,
            textAmountY,
            amountTextPaint
        )
        canvas.drawText(
            textAmountStr,
            textAmountXDescription,
            textAmountYDescription,
            descriptionTextPain
        )
    }

    /**
     * Метод инициализации переданной TextPaint
     */
    private fun initPains(
        textPaint: TextPaint,
        textSize: Float,
        textColor: Int,
        isDescription: Boolean = false
    ) {
        textPaint.color = textColor
        textPaint.textSize = textSize
        textPaint.isAntiAlias = true

        if (!isDescription) textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    /**
     * Метод получения размера View по переданному Mode.
     */
    private fun resolveDefaultSize(spec: Int, defValue: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> context.dpToPx(defValue).toInt()
            else -> MeasureSpec.getSize(spec)
        }
    }

    /**
     * Метод расчёта высоты всего текста, включая отступы.
     */
    private fun calculateViewHeight(heightMeasureSpec: Int): Int {
        val initSizeHeight = resolveDefaultSize(heightMeasureSpec, DEFAULT_VIEW_SIZE_HEIGHT)
        textHeight = (dataList.size * marginText).toInt()

        val textHeightWithPadding = textHeight + paddingTop + paddingBottom
        return if (textHeightWithPadding > initSizeHeight) textHeightWithPadding else initSizeHeight
    }

    /**
     * Метод расчёта радиуса круговой диаграммы, установка координат для отрисовки.
     */
    private fun calculateCircleRadius(width: Int, height: Int) {
        val circleViewWidth = (width * CIRCLE_WIDTH_PERCENT)
        circleRadius = if (circleViewWidth > height) {
            (height.toFloat() - circlePadding) / 2
        } else {
            circleViewWidth.toFloat() / 2
        }

        with(circleRect) {
            left = circlePadding
            top = height / 2 - circleRadius
            right = circleRadius * 2 + circlePadding
            bottom = height / 2 + circleRadius
        }

        circleCenterX = (circleRadius * 2 + circlePadding + circlePadding) / 2
        circleCenterY = (height / 2 + circleRadius + (height / 2 - circleRadius)) / 2

        textAmountY = circleCenterY - 10

        val sizeTextAmountNumber = getWidthOfAmountText(amountForm(totalAmount), amountTextPaint)

        textAmountXNumber = circleCenterX - sizeTextAmountNumber.width() / 2
        textAmountXDescription =
            circleCenterX - getWidthOfAmountText(textAmountStr, descriptionTextPain).width() / 2
        textAmountYDescription = (circleCenterY + sizeTextAmountNumber.height() + marginTextThird) + 10
    }

    /**
     * Метод заполнения поля [percentageCircleList]
     */
    private fun calculatePercentageOfData() {
        var startAt = circleSectionSpace
        percentageCircleList = dataList.mapIndexed { index, pair ->
            var percent = pair * 100 / dataList.fold(0) { res, value ->
                res + value
            }.toFloat() - circleSectionSpace
            percent = if (percent < 0F) 0F else percent

            val resultModel = AnalyticalPieChartModel(
                percentOfCircle = percent,
                percentToStartAt = startAt,
                colorOfLine = Color.parseColor(pieChartColors[index % pieChartColors.size]),
                stroke = circleStrokeWidth,
                paintRound = circlePaintRoundSize
            )
            if (percent != 0F) startAt += percent + circleSectionSpace
            resultModel
        }
    }

    /**
     * Метод обертки текста в класс [Rect]
     */
    private fun getWidthOfAmountText(text: String, textPaint: TextPaint): Rect {
        val bounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, bounds)
        return bounds
    }

    /**
     * Метод обертки суммы [Rect]
     */
    private fun amountForm(int: Int) = String.format("%,d", int).replace(',', ' ')
}