package hu.ait.minesweeper.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.minesweeper.MainActivity
import hu.ait.minesweeper.R
import hu.ait.minesweeper.model.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBackground = Paint()
    private var paintLine = Paint()
    private var bitmapImg = BitmapFactory.decodeResource(resources, R.drawable.skateboardd)
    private val paintText = Paint()
    private var lock = false


    init{
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        paintText.color = Color.GREEN
        paintText.textSize = 100f
    }

    //what does this actually do...
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmapImg = Bitmap.createScaledBitmap(bitmapImg, w/5, h/5, false)
        paintText.textSize = h / 5f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        drawBoard(canvas)

        drawMine(canvas)
    }

    //rename this
    fun drawMine(canvas: Canvas?) {
        for (i in 0..4) { //go back and change this later so it's not hard-coded
            for (j in 0..4) {
                if (MinesweeperModel.getField(i, j).type == MinesweeperModel.MINE && MinesweeperModel.getField(i, j).wasClicked) {
                    canvas?.drawBitmap(bitmapImg, i*height/5f, j*width/5f, null)
                } else if(MinesweeperModel.getField(i, j).type == MinesweeperModel.CLEAR && MinesweeperModel.getField(i, j).wasClicked){
                    var num = MinesweeperModel.getField(i, j).minesAround
                    canvas?.drawText("$num", i*height/5f, (j+1)*width/5f, paintText)
                } else if(MinesweeperModel.getField(i, j).isFlagged) {
                    canvas?.drawText("F", i*height/5f, (j+1)*width/5f, paintText)
                }
            }

        }
    }

    private fun drawBoard(canvas: Canvas?) {
        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        for(i in 1..4) {
            canvas?.drawLine(0f, (i * height / 5).toFloat(), width.toFloat(), (i * height / 5).toFloat(),
                paintLine)
            canvas?.drawLine((i * width / 5).toFloat(), 0f, (i * width / 5).toFloat(), height.toFloat(),
                paintLine)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && !lock) {
            val tX = event.x.toInt() / (width / 5)
            val tY = event.y.toInt() / (height / 5)

            if ((context as MainActivity).isFlagModeOn()) {
                if(!MinesweeperModel.flag(tX, tY)) {
                    loseGame()
                }
            } else {
                if(MinesweeperModel.testSquare(tX, tY)) {
                    loseGame()
                }
            }
            if(MinesweeperModel.checkWin()) {
                winGame()
            }
        }
        invalidate()
        return true
    }

    //what does this do exactly...
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    fun resetGame() {
        MinesweeperModel.resetModel()
        (context as MainActivity).setMessage("Minesweeper time")
        (context as MainActivity).resetCB()
        lock = false
        invalidate()
    }

    private fun loseGame() {
        for(i in 0..3) {
            MinesweeperModel.getField(MinesweeperModel.minesList[i][0], MinesweeperModel.minesList[i][1]).setClicked(true)
        }
        lock = true
        (context as MainActivity).setMessage("Game over!")
    }

    private fun winGame() {
        lock = true
        (context as MainActivity).setMessage("You win!")
    }
}