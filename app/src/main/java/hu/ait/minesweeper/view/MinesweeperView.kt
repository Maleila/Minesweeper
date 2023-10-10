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
import android.widget.Toast
import hu.ait.minesweeper.MainActivity
import hu.ait.minesweeper.R
import hu.ait.minesweeper.model.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBackground = Paint()
    private var paintLine = Paint()
    private var bitmapImg = BitmapFactory.decodeResource(resources, R.drawable.mine)
    private var bitmapFlag = BitmapFactory.decodeResource(resources, R.drawable.flag)
    private val paintText = Paint()
    private var lock = false //used to disable the user from clicking the board after the game is over

    init{
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        paintText.color = Color.GREEN
        paintText.textSize = 100f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmapImg = Bitmap.createScaledBitmap(bitmapImg, w/5, h/5, false)
        bitmapFlag = Bitmap.createScaledBitmap(bitmapFlag, w/5, h/5, false)
        paintText.textSize = h / 5f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        drawBoard(canvas)

        drawTiles(canvas)
    }

    private fun drawTiles(canvas: Canvas?) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (MinesweeperModel.getField(i, j).type == MinesweeperModel.MINE && MinesweeperModel.getField(i, j).wasClicked) {
                    canvas?.drawBitmap(bitmapImg, i*height/5f, j*width/5f, null)
                } else if(MinesweeperModel.getField(i, j).type == MinesweeperModel.CLEAR && MinesweeperModel.getField(i, j).wasClicked){
                    centerNumber(canvas, i, j)
                } else if(MinesweeperModel.getField(i, j).isFlagged) {
                    canvas?.drawBitmap(bitmapFlag, i*height/5f, j*width/5f, null)
                }
            }
        }
    }

    private fun centerNumber(canvas: Canvas?, i: Int, j: Int) {
        var num = MinesweeperModel.getField(i, j).minesAround
        val offsetY = (height/5f - (paintText.ascent() + paintText.descent()))/2f
        val offsetX = (width/5f - paintText.measureText("$num"))/2f
        canvas?.drawText("$num", offsetX + i*height/5f, offsetY + (j*width/5f), paintText)
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
            } else if(MinesweeperModel.testMine(tX, tY)) {
                    loseGame()
            }
            if(MinesweeperModel.checkWin()) {
                winGame()
            }
        }
        invalidate()
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    fun resetGame() {
        MinesweeperModel.resetModel()
        (context as MainActivity).resetCB()
        (context as MainActivity).resetTimer()
        lock = false
        invalidate()
    }

    private fun loseGame() {
        for(i in 0..2) {
            MinesweeperModel.getField(MinesweeperModel.minesList[i][0], MinesweeperModel.minesList[i][1]).setClicked()
        }
        lock = true
        (context as MainActivity).stopTimer()
        Toast.makeText((context as MainActivity),
            context.getString(R.string.text_lose), Toast.LENGTH_SHORT).show()
    }

    private fun winGame() {
        lock = true
        (context as MainActivity).stopTimer()
        Toast.makeText((context as MainActivity),
            context.getString(R.string.text_win), Toast.LENGTH_SHORT).show()
    }
}