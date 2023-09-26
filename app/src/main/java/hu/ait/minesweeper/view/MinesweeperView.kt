package hu.ait.minesweeper.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBackground = Paint()
    private var paintLine = Paint()

    init{
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
    }

    //what does this actually do...
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        drawBoard(canvas)
    }

    private fun drawBoard(canvas: Canvas?) {
        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        canvas?.drawLine(0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(),
            paintLine)
        canvas?.drawLine(0f, (2 * height / 3).toFloat(), width.toFloat(),
            (2 * height / 3).toFloat(), paintLine)

        // two vertical lines
        canvas?.drawLine((width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(),
            paintLine)
        canvas?.drawLine((2 * width / 3).toFloat(), 0f, (2 * width / 3).toFloat(), height.toFloat(),
            paintLine)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / 3)
            val tY = event.y.toInt() / (height / 3)
        }
        print("touch event!")
        return true
    }

    //what does this do exactly...
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

}