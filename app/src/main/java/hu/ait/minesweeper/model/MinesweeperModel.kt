package hu.ait.minesweeper.model

import hu.ait.minesweeper.view.MinesweeperView

object MinesweeperModel {

    public val CLEAR: Int = 0
    public val MINE: Int = 1

    //should probably put this in a loop later...
    val fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
        arrayOf(Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false), Field(CLEAR, 0, false, false)),
    )

    val minesList: Array<Array<Int>> = arrayOf(arrayOf(0, 0), arrayOf(0, 0), arrayOf(0, 0), arrayOf(0, 0))
    private var notMines = 0

    init {
        resetModel()
    }

    fun resetModel() {
        //reset back to empty model
        for (i in 0..fieldMatrix.size - 1) {
            for (j in 0..fieldMatrix[i].size -1 ) {
                fieldMatrix[i][j] = Field(CLEAR, 0, false, false)
            }
        }
        //add mines (number of mines should probably be modifiable later)
        for(i in 0..3) {
//            var r = (0..4).random()
//            var c = (0..4).random()
            var r = i
            var c = i
            fieldMatrix[r][c].changeType(MINE)
            updateNeighbors(r, c)
            minesList[i] = arrayOf(r, c)
            //MinesweeperView.drawMine(r, c)
        }
        notMines = fieldMatrix.size* fieldMatrix[0].size - minesList.size
        println(notMines)
    }

    fun updateNeighbors(r: Int, c: Int) {
        var up = r > 0
        var down = r < fieldMatrix.size - 1
        var right = c < fieldMatrix[r].size - 1
        var left = c > 0

        if(up) {
            fieldMatrix[r-1][c].addMineNearby()
        }
        if(down) {
            fieldMatrix[r+1][c].addMineNearby()
        }
        if(left) {
            fieldMatrix[r][c-1].addMineNearby()
        }
        if(right) {
            fieldMatrix[r][c+1].addMineNearby()
        }
        if(up && left) {
            fieldMatrix[r-1][c-1].addMineNearby()
        }
        if(up && right) {
            fieldMatrix[r-1][c+1].addMineNearby()
        }
        if(down && left){
            fieldMatrix[r+1][c-1].addMineNearby()
        }
        if(down && right) {
            fieldMatrix[r+1][c+1].addMineNearby()
        }
    }

    //rename this I keep forgetting what true/false means
    fun testSquare(r: Int, c: Int): Boolean {
        fieldMatrix[r][c].setClicked(true)
        if(fieldMatrix[r][c].type == MINE) {
            return true
        } else {
            notMines --
            return false
        }
    }

    fun checkWin(): Boolean {
        if(notMines == 0) //if there are still untried clear squares, return false
            return true
        for(m in minesList) { //if there are still undiscovered mines, return false
            if(!fieldMatrix[m[0]][m[1]].isFlagged) {
                return false
            }
        }
        return true
    }

    //not sure if this is how I wanna do it -- seems like iffy encapsulation
    fun getField(r: Int, c: Int): Field {
        return fieldMatrix[r][c]
    }

    fun flag(r: Int, c: Int): Boolean {
        fieldMatrix[r][c].flag()
        if(fieldMatrix[r][c].type == MINE)
            return true
        return false
    }
}